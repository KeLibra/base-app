package cn.vastsky.lib.base.common.view.weight.img;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView.ScaleType;

import androidx.annotation.ColorInt;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description
 */
public class RoundDrawable  extends Drawable {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width > 0 && height > 0) {
            bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        } else {
            //适配纯色背景
            bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Drawable fromDrawable(Drawable drawable, float radius) {
        return fromDrawable(drawable, radius, 0, 0, false);
    }

    public static Drawable fromDrawable(Drawable drawable, float radius,
                                        int border, int borderColor, boolean isCircle) {
        if (drawable != null) {
            if (drawable instanceof TransitionDrawable) {
                TransitionDrawable td = (TransitionDrawable) drawable;
                int num = td.getNumberOfLayers();

                Drawable[] drawableList = new Drawable[num];
                for (int i = 0; i < num; i++) {
                    Drawable d = td.getDrawable(i);
                    if (d instanceof ColorDrawable) {
                        // TODO skip colordrawables for now
                        drawableList[i] = d;
                    } else {
                        drawableList[i] = new RoundDrawable(
                                drawableToBitmap(td.getDrawable(i)), radius,
                                border, borderColor, isCircle);
                    }
                }
                return new TransitionDrawable(drawableList);
            }

            Bitmap bm = drawableToBitmap(drawable);
            if (bm != null) {
                return new RoundDrawable(bm, radius, border, borderColor,
                        isCircle);
            }
        }
        return drawable;
    }

    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();
    private float mCornerRadius;
    private final RectF mBitmapRect = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mBitmapPaint;

    private Paint colorPaint;
    private/* final */ int mBitmapWidth;
    private/* final */ int mBitmapHeight;
    private final RectF mBorderRect = new RectF();

    private final Paint mBorderPaint;

    private Bitmap mBitmap;
    private int mBorderWidth;

    // private boolean mIsCircle;

    private int mBorderColor;

    private ScaleType mScaleType = ScaleType.FIT_XY;

    private final Matrix mShaderMatrix = new Matrix();

    private boolean isDown = false;

    RoundDrawable(Bitmap bitmap, float cornerRadius, int border,
                  int borderColor) {
        this(bitmap, cornerRadius, border, borderColor, false);
    }

    public RoundDrawable(Bitmap bitmap, float cornerRadius, int border,
                         int borderColor, boolean isCircle) {
        mBitmap = bitmap;
        mBorderWidth = border;
        mBorderColor = borderColor;

        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();

        if (isCircle) {
            float widthScale = (mBitmapWidth / cornerRadius);
            float heightScale = (mBitmapHeight / cornerRadius);
            if (widthScale > 1 || heightScale > 1) {
                if (widthScale > heightScale) {
                    mBitmapWidth = (int) (mBitmapWidth / widthScale);
                    mBitmapHeight = (int) (mBitmapHeight / widthScale);
                } else {
                    mBitmapWidth = (int) (mBitmapWidth / heightScale);
                    mBitmapHeight = (int) (mBitmapHeight / heightScale);
                }
            }
        }

        mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);

        mCornerRadius = cornerRadius;
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(mShaderMatrix);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setShader(mBitmapShader);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(border);

        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        colorPaint.setColor(Color.BLACK);
        colorPaint.setAlpha(100);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mBorderWidth > 0) {
            canvas.drawRoundRect(mBorderRect, mCornerRadius, mCornerRadius,
                    mBorderPaint);
            canvas.drawRoundRect(mDrawableRect,
                    Math.max(mCornerRadius - mBorderWidth, 0),
                    Math.max(mCornerRadius - mBorderWidth, 0), mBitmapPaint);

        } else {
            canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius,
                    mBitmapPaint);
        }
        if (isDown) {
            canvas.drawRoundRect(mDrawableRect, mCornerRadius, mCornerRadius,
                    colorPaint);
        }

    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public float getCornerRadius() {
        return mCornerRadius;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mBounds.set(bounds);

        setMatrix();
    }

    @Override
    public void setAlpha(int alpha) {
        mBitmapPaint.setAlpha(alpha);
    }

    public void setBorderColor(int color) {
        this.mBorderColor = color;
        mBorderPaint.setColor(color);
    }

    public void setBorderWidth(int width) {
        this.mBorderWidth = width;
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mBitmapPaint.setColorFilter(cf);
    }

    public void setCornerRadius(float radius) {
        this.mCornerRadius = radius;
    }

    private void setMatrix() {
        mBorderRect.set(mBounds);
        mDrawableRect.set(0 + mBorderWidth, 0 + mBorderWidth,
                mBorderRect.width() - mBorderWidth, mBorderRect.height()
                        - mBorderWidth);

        float scale;
        float dx;
        float dy;

        switch (mScaleType) {
            case CENTER:
                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mBorderWidth, 0 + mBorderWidth,
                        mBorderRect.width() - mBorderWidth, mBorderRect.height()
                                - mBorderWidth);

                mShaderMatrix.set(null);
                mShaderMatrix
                        .setTranslate(
                                (int) ((mDrawableRect.width() - mBitmapWidth) * 0.5f + 0.5f),
                                (int) ((mDrawableRect.height() - mBitmapHeight) * 0.5f + 0.5f));
                break;
            case CENTER_CROP:
                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mBorderWidth, 0 + mBorderWidth,
                        mBorderRect.width() - mBorderWidth, mBorderRect.height()
                                - mBorderWidth);

                mShaderMatrix.set(null);

                dx = 0;
                dy = 0;

                if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()
                        * mBitmapHeight) {
                    scale = (float) mDrawableRect.height() / (float) mBitmapHeight;
                    dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = (float) mDrawableRect.width() / (float) mBitmapWidth;
                    dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth,
                        (int) (dy + 0.5f) + mBorderWidth);
                break;
            case CENTER_INSIDE:
                mShaderMatrix.set(null);

                if (mBitmapWidth <= mBounds.width()
                        && mBitmapHeight <= mBounds.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min(
                            (float) mBounds.width() / (float) mBitmapWidth,
                            (float) mBounds.height() / (float) mBitmapHeight);
                }

                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mBorderWidth, mBorderRect.top
                                + mBorderWidth, mBorderRect.right - mBorderWidth,
                        mBorderRect.bottom - mBorderWidth);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_CENTER:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
                        Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mBorderWidth, mBorderRect.top
                                + mBorderWidth, mBorderRect.right - mBorderWidth,
                        mBorderRect.bottom - mBorderWidth);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_END:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
                        Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mBorderWidth, mBorderRect.top
                                + mBorderWidth, mBorderRect.right - mBorderWidth,
                        mBorderRect.bottom - mBorderWidth);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_START:
                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds,
                        Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mBorderWidth, mBorderRect.top
                                + mBorderWidth, mBorderRect.right - mBorderWidth,
                        mBorderRect.bottom - mBorderWidth);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect,
                        Matrix.ScaleToFit.FILL);
                break;
            case FIT_XY:
            default:
                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mBorderWidth, 0 + mBorderWidth,
                        mBorderRect.width() - mBorderWidth, mBorderRect.height()
                                - mBorderWidth);
                mShaderMatrix.set(null);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect,
                        Matrix.ScaleToFit.FILL);
                break;
        }
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    public void setPressDown(boolean isDown) {
        this.isDown = isDown;
        invalidateSelf();
    }

    public void setPressDownColor(@ColorInt int color) {
        if (colorPaint != null) {
            colorPaint.setColor(color);
            invalidateSelf();
        }
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ScaleType.FIT_XY;
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType;
            setMatrix();
        }
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
