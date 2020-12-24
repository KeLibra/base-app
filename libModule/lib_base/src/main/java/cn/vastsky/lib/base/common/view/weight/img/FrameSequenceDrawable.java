package cn.vastsky.lib.base.common.view.weight.img;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

public class FrameSequenceDrawable extends Drawable implements Animatable, Runnable {
    private static final String TAG = "FrameSequence";
    /**
     * These constants are chosen to imitate common browser behavior for WebP/GIF.
     * If other decoders are added, this behavior should be moved into the WebP/GIF decoders.
     *
     * Note that 0 delay is undefined behavior in the GIF standard.
     */
    private static final long MIN_DELAY_MS = 20;
    private static final long DEFAULT_DELAY_MS = 100;

    private static final Object sLock = new Object();
    private static HandlerThread sDecodingThread;
    private static Handler sDecodingThreadHandler;
    private static void initializeDecodingThread() {
        synchronized (sLock) {
            if (sDecodingThread != null) return;

            sDecodingThread = new HandlerThread("FrameSequence decoding thread",
                    Process.THREAD_PRIORITY_BACKGROUND);
            sDecodingThread.start();
            sDecodingThreadHandler = new Handler(sDecodingThread.getLooper());
        }
    }

    public static interface OnFinishedListener {
        /**
         * Called when a FrameSequenceDrawable has finished looping.
         *
         * Note that this is will not be called if the drawable is explicitly
         * stopped, or marked invisible.
         */
        public abstract void onFinished(FrameSequenceDrawable drawable);
    }

    public static interface BitmapProvider {
        /**
         * Called by FrameSequenceDrawable to aquire an 8888 Bitmap with minimum dimensions.
         */
        public abstract Bitmap acquireBitmap(int minWidth, int minHeight);

        /**
         * Called by FrameSequenceDrawable to release a Bitmap it no longer needs. The Bitmap
         * will no longer be used at all by the drawable, so it is safe to reuse elsewhere.
         *
         * This method may be called by FrameSequenceDrawable on any thread.
         */
        public abstract void releaseBitmap(Bitmap bitmap);
    }

    private static BitmapProvider sAllocatingBitmapProvider = new BitmapProvider() {
        @Override
        public Bitmap acquireBitmap(int minWidth, int minHeight) {

            return Bitmap.createBitmap(minWidth, minHeight, Bitmap.Config.ARGB_8888);
        }

        @Override
        public void releaseBitmap(Bitmap bitmap) {
        }
    };

    /**
     * Register a callback to be invoked when a FrameSequenceDrawable finishes looping.
     *
     * @see #setLoopBehavior(int)
     */
    public void setOnFinishedListener(OnFinishedListener onFinishedListener) {
        mOnFinishedListener = onFinishedListener;
    }

    /**
     * Loop a finite number of times, which can be set using setLoopCount. Default to loop once.
     */
    public static final int LOOP_FINITE = 1;

    /**
     * Loop continuously. The OnFinishedListener will never be called.
     */
    public static final int LOOP_INF = 2;

    /**
     * Use loop count stored in source data, or LOOP_ONCE if not present.
     */
    public static final int LOOP_DEFAULT = 3;

    /**
     * Loop only once.
     *
     * @deprecated Use LOOP_FINITE instead.
     */
    @Deprecated
    public static final int LOOP_ONCE = LOOP_FINITE;

    /**
     * Define looping behavior of frame sequence.
     *
     * Must be one of LOOP_ONCE, LOOP_INF, LOOP_DEFAULT, or LOOP_FINITE.
     */
    public void setLoopBehavior(int loopBehavior) {
        mLoopBehavior = loopBehavior;
    }

    /**
     * Set the number of loops in LOOP_FINITE mode. The number must be a postive integer.
     */
    public void setLoopCount(int loopCount) {
        mLoopCount = loopCount;
    }

    private final FrameSequence mFrameSequence;
    private final FrameSequence.State mFrameSequenceState;

    private final Paint mPaint;
    private BitmapShader mFrontBitmapShader;
    private BitmapShader mBackBitmapShader;
    private final Rect mSrcRect;

    //Protects the fields below
    private final Object mLock = new Object();

    private final BitmapProvider mBitmapProvider;
    private boolean mDestroyed = false;
    private Bitmap mFrontBitmap;
    private Bitmap mBackBitmap;

    private static final int STATE_SCHEDULED = 1;
    private static final int STATE_DECODING = 2;
    private static final int STATE_WAITING_TO_SWAP = 3;
    private static final int STATE_READY_TO_SWAP = 4;

    private int mState;
    private int mCurrentLoop;
    private int mLoopBehavior = LOOP_INF;
    private int mLoopCount = 1;

    private long mLastSwap;
    private long mNextSwap;
    private int mNextFrameToDecode;
    private OnFinishedListener mOnFinishedListener;

    private RectF mTempRectF = new RectF();

    private final static Handler sUiHandler = new Handler(Looper.getMainLooper());

    /**
     * Runs on decoding thread, only modifies mBackBitmap's pixels
     */
    private Runnable mDecodeRunnable = new Runnable() {
        @Override
        public void run() {
            int nextFrame;
            Bitmap bitmap;
            synchronized (mLock) {
                if (mDestroyed) return;

                nextFrame = mNextFrameToDecode;
                if (nextFrame < 0) {
                    return;
                }
                bitmap = mBackBitmap;
                mState = STATE_DECODING;
            }
            int lastFrame = nextFrame - 2;
            boolean exceptionDuringDecode = false;
            long invalidateTimeMs = 0;
            try {
                invalidateTimeMs = mFrameSequenceState.getFrame(nextFrame, bitmap, lastFrame);
            } catch(Exception e) {
                // Exception during decode: continue, but delay next frame indefinitely.
                Log.e(TAG, "exception during decode: " + e);
                exceptionDuringDecode = true;
            }

            if (invalidateTimeMs < MIN_DELAY_MS) {
                invalidateTimeMs = DEFAULT_DELAY_MS;
            }

            boolean schedule = false;
            Bitmap bitmapToRelease = null;
            synchronized (mLock) {
                if (mDestroyed) {
                    bitmapToRelease = mBackBitmap;
                    mBackBitmap = null;
                } else if (mNextFrameToDecode >= 0 && mState == STATE_DECODING) {
                    schedule = true;
                    mNextSwap = exceptionDuringDecode ? Long.MAX_VALUE : invalidateTimeMs + mLastSwap;
                    mState = STATE_WAITING_TO_SWAP;
                }
            }
            if (schedule) {
                try{
                    scheduleSelf(FrameSequenceDrawable.this, mNextSwap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (bitmapToRelease != null) {
                // destroy the bitmap here, since there's no safe way to get back to
                // drawable thread - drawable is likely detached, so schedule is noop.
                mBitmapProvider.releaseBitmap(bitmapToRelease);
            }
        }
    };

    private Runnable mFinishedCallbackRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (mLock) {
                mNextFrameToDecode = -1;
                mState = 0;
            }
            if (mOnFinishedListener != null) {
                mOnFinishedListener.onFinished(FrameSequenceDrawable.this);
            }
        }
    };

    private static Bitmap acquireAndValidateBitmap(BitmapProvider bitmapProvider,
                                                   int minWidth, int minHeight) {
        Bitmap bitmap = bitmapProvider.acquireBitmap(minWidth, minHeight);

        if (bitmap.getWidth() < minWidth
                || bitmap.getHeight() < minHeight
                || bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new IllegalArgumentException("Invalid bitmap provided");
        }

        return bitmap;
    }

    public FrameSequenceDrawable(FrameSequence frameSequence) {
        this(frameSequence, sAllocatingBitmapProvider);
    }

    public FrameSequenceDrawable(FrameSequence frameSequence, BitmapProvider bitmapProvider) {
        if (frameSequence == null || bitmapProvider == null) throw new IllegalArgumentException();

        mFrameSequence = frameSequence;
        mFrameSequenceState = frameSequence.createState();
        final int width = frameSequence.getWidth();
        final int height = frameSequence.getHeight();

        mBitmapProvider = bitmapProvider;
        mFrontBitmap = acquireAndValidateBitmap(bitmapProvider, width, height);
        mBackBitmap = acquireAndValidateBitmap(bitmapProvider, width, height);
        mSrcRect = new Rect(0, 0, width, height);
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);
        mPaint.setAntiAlias(true);

        mFrontBitmapShader
                = new BitmapShader(mFrontBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBackBitmapShader
                = new BitmapShader(mBackBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mLastSwap = 0;

        mNextFrameToDecode = -1;
        mFrameSequenceState.getFrame(0, mFrontBitmap, -1);
        initializeDecodingThread();
    }

    public final void setRadius(float rx, float ry){

        if(rx < 0 || ry < 0){

            return;
        }

        if(mRx == rx && mRy == ry){

            return;
        }

        mRx = rx;
        mRy = ry;

        invalidateSelf();
    }

    public Bitmap getFirstFrame(){

        final int width = mFrameSequence.getWidth();
        final int height = mFrameSequence.getHeight();

        Bitmap bmp = mBitmapProvider.acquireBitmap(width,height);

        mFrameSequenceState.getFrame(0,bmp,-1);

        return bmp;
    }

    private void checkDestroyedLocked() {
        if (mDestroyed) {
            throw new IllegalStateException("Cannot perform operation on recycled drawable");
        }
    }

    public boolean isDestroyed() {
        synchronized (mLock) {
            return mDestroyed;
        }
    }

    /**
     * Marks the drawable as permanently recycled (and thus unusable), and releases any owned
     * Bitmaps drawable to its BitmapProvider, if attached.
     *
     * If no BitmapProvider is attached to the drawable, recycle() is called on the Bitmaps.
     */
    public void destroy() {
        if (mBitmapProvider == null) {
            throw new IllegalStateException("BitmapProvider must be non-null");
        }

        Bitmap bitmapToReleaseA;
        Bitmap bitmapToReleaseB = null;
        synchronized (mLock) {
            checkDestroyedLocked();

            bitmapToReleaseA = mFrontBitmap;
            mFrontBitmap = null;

            if (mState != STATE_DECODING) {
                bitmapToReleaseB = mBackBitmap;
                mBackBitmap = null;
            }

            mDestroyed = true;
        }

        // For simplicity and safety, we don't destroy the state object here
        mBitmapProvider.releaseBitmap(bitmapToReleaseA);
        if (bitmapToReleaseB != null) {
            mBitmapProvider.releaseBitmap(bitmapToReleaseB);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            mFrameSequenceState.destroy();
        } finally {
            super.finalize();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (mLock) {
            checkDestroyedLocked();
            if (mState == STATE_WAITING_TO_SWAP) {
                // may have failed to schedule mark ready runnable,
                // so go ahead and swap if swapping is due
                if (mNextSwap - SystemClock.uptimeMillis() <= 0) {
                    mState = STATE_READY_TO_SWAP;
                }
            }

            if (isRunning() && mState == STATE_READY_TO_SWAP) {
                // Because draw has occurred, the view system is guaranteed to no longer hold a
                // reference to the old mFrontBitmap, so we now use it to produce the next frame
                Bitmap tmp = mBackBitmap;
                mBackBitmap = mFrontBitmap;
                mFrontBitmap = tmp;

                BitmapShader tmpShader = mBackBitmapShader;
                mBackBitmapShader = mFrontBitmapShader;
                mFrontBitmapShader = tmpShader;

                mLastSwap = SystemClock.uptimeMillis();

                boolean continueLooping = true;
                if (mNextFrameToDecode == mFrameSequence.getFrameCount() - 1) {
                    mCurrentLoop++;
                    if ((mLoopBehavior == LOOP_FINITE && mCurrentLoop == mLoopCount) ||
                            (mLoopBehavior == LOOP_DEFAULT && mCurrentLoop == mFrameSequence.getDefaultLoopCount()) ||
                            getFrameCount() <= 1) {
                        continueLooping = false;
                    }
                }

                if (continueLooping) {
                    scheduleDecodeLocked();
                } else {
                    scheduleSelf(mFinishedCallbackRunnable, 0);
                }
            }
        }

        mPaint.setShader(mFrontBitmapShader);

        mTempRectF.set(getBounds());

        if(mShaderMatrix == null) {

            mShaderMatrix = new Matrix();

            initMatrix(getIntrinsicWidth(), getIntrinsicHeight(), mTempRectF);
        }

        mFrontBitmapShader.setLocalMatrix(mShaderMatrix);

        canvas.drawRoundRect(mTempRectF,mRx,mRy,mPaint);
    }

    private float mRx;
    private float mRy;

    private void scheduleDecodeLocked() {
        mState = STATE_SCHEDULED;
        mNextFrameToDecode = (mNextFrameToDecode + 1) % mFrameSequence.getFrameCount();
        sDecodingThreadHandler.removeCallbacks(mDecodeRunnable);
        sDecodingThreadHandler.post(mDecodeRunnable);
    }

    public void seekTo(int frameNumber){

        if(frameNumber < 0 || frameNumber >= mFrameSequence.getFrameCount()){

            return;
        }

        if(isRunning()){

            return;
        }

        mFrameSequenceState.getFrame(frameNumber,mFrontBitmap,-1);

        invalidateSelf();
    }

    public int getFrameCount(){

        return mFrameSequence == null ? 0 : mFrameSequence.getFrameCount();
    }

    @Override
    public void run() {
        // set ready to swap as necessary
        boolean invalidate = false;
        synchronized (mLock) {
            if (mNextFrameToDecode >= 0 && mState == STATE_WAITING_TO_SWAP) {
                mState = STATE_READY_TO_SWAP;
                invalidate = true;
            }
        }
        if (invalidate) {
            invalidateSelf();
        }
    }

    @Override
    public void start() {
        if (!isRunning()) {
            synchronized (mLock) {
                checkDestroyedLocked();
                if (mState == STATE_SCHEDULED) return; // already scheduled
                mCurrentLoop = 0;
                scheduleDecodeLocked();
            }
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            unscheduleSelf(this);
        }
    }

    @Override
    public boolean isRunning() {
        synchronized (mLock) {
            return mNextFrameToDecode > -1 && !mDestroyed;
        }
    }

    @Override
    public void unscheduleSelf(Runnable what) {
        synchronized (mLock) {
            mNextFrameToDecode = -1;
            mState = 0;
        }
        super.unscheduleSelf(what);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);

        if(!mHandleSetVisible){

            return changed;
        }

        if (!visible) {
            stop();
        } else if (restart || changed) {
            stop();
            start();
        }

        return changed;
    }

    // drawing properties

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getIntrinsicWidth() {
        return mFrameSequence.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mFrameSequence.getHeight();
    }

    @Override
    public int getOpacity() {
        return mFrameSequence.isOpaque() ? PixelFormat.OPAQUE : PixelFormat.TRANSPARENT;
    }

    private boolean mHandleSetVisible = true;

    public void setHandleSetVisible(boolean isHandleSetVisible){

        mHandleSetVisible = isHandleSetVisible;
    }

    public void setScaleType(ImageView.ScaleType scaleType){

        if(mScaleType != scaleType){

            mScaleType = scaleType;

            mShaderMatrix = null;

            invalidateSelf();
        }
    }

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_INSIDE;
    private RectF mBitmapRect = new RectF();
    private Matrix mShaderMatrix;

    private void initMatrix(int bitmapWidth, int bitmapHeight,
                            RectF dstRect) {

        if(mScaleType == null){

            return;
        }

        mBitmapRect.set(0,0,bitmapWidth,bitmapHeight);

        float scale;
        float dx;
        float dy;

        switch (mScaleType) {
            case CENTER:


                mShaderMatrix.set(null);
                mShaderMatrix
                        .setTranslate(
                                (int) ((dstRect.width() - bitmapWidth) * 0.5f + 0.5f),
                                (int) ((dstRect.height() - bitmapHeight) * 0.5f + 0.5f));
                break;
            case CENTER_CROP:


                mShaderMatrix.set(null);

                dx = 0;
                dy = 0;

                if (bitmapWidth * dstRect.height() > dstRect.width()
                        * bitmapHeight) {
                    scale = (float) dstRect.height() / (float) bitmapHeight;
                    dx = (dstRect.width() - bitmapWidth * scale) * 0.5f;
                } else {
                    scale = (float) dstRect.width() / (float) bitmapWidth;
                    dy = (dstRect.height() - bitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f),
                        (int) (dy + 0.5f));
                break;
            case CENTER_INSIDE:
                mShaderMatrix.set(null);

                if (bitmapWidth <= dstRect.width()
                        && bitmapHeight <= dstRect.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min(
                            (float) dstRect.width() / (float) bitmapWidth,
                            (float) dstRect.height() / (float) bitmapHeight);
                }

                dx = (int) ((dstRect.width() - bitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((dstRect.height() - bitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mShaderMatrix.mapRect(dstRect);

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_CENTER:

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.CENTER);

                mShaderMatrix.mapRect(dstRect);


                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_END:

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(dstRect);

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_START:

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(dstRect);

                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_XY:
            default:

                mShaderMatrix.set(null);
                mShaderMatrix.setRectToRect(mBitmapRect, dstRect,
                        Matrix.ScaleToFit.FILL);
                break;
        }
    }
}

