package cn.vastsky.lib.base.common.view.weight.img;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import cn.vastsky.lib.base.R;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description  圆角图片
 */
public class RoundImageView extends ImageView {
    public static final String TAG = "RoundImageView";

    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_BORDER = 0;
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private int mCornerRadius;
    private int mBorderWidth;
    private int mBorderColor;
    private int mBorderBgColor;

    private boolean roundBackground;

    private Drawable mDrawable;
    private Drawable mBackgroundDrawable;

    private ScaleType mScaleType;

    private boolean hasPressDownShade = false;

    private boolean mIsCircle;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private boolean mUseCache = true;
    private static final ScaleType[] sScaleTypeArray = {ScaleType.MATRIX,
            ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER,
            ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE};

    public RoundImageView(Context context) {
        super(context);
        mCornerRadius = DEFAULT_RADIUS;
        mBorderWidth = DEFAULT_BORDER;
        mBorderColor = DEFAULT_BORDER_COLOR;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView, defStyle, 0);

        int index = a
                .getInt(R.styleable.RoundImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(sScaleTypeArray[index]);
        }
        hasPressDownShade = a.getBoolean(
                R.styleable.RoundImageView_pressdown_shade, false);
        mCornerRadius = a.getDimensionPixelSize(
                R.styleable.RoundImageView_corner_radius, -1);
        mBorderWidth = a.getDimensionPixelSize(
                R.styleable.RoundImageView_border_width, -1);

        // don't allow negative values for radius and border
        if (mCornerRadius < 0) {
            mCornerRadius = DEFAULT_RADIUS;
        }
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER;
        }

        mBorderColor = a.getColor(R.styleable.RoundImageView_border_color,
                DEFAULT_BORDER_COLOR);

        mBorderBgColor = a.getColor(R.styleable.RoundImageView_border_bg_color,
                DEFAULT_BORDER_COLOR);

        roundBackground = a.getBoolean(
                R.styleable.RoundImageView_round_background, false);
        setBackgroundDrawable(getBackground());
        setImageDrawable(getDrawable());
        a.recycle();
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    public int getBorder() {
        return mBorderWidth;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public void setBorderBgColor(int color) {
        mBorderBgColor = color;
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     * @see ImageView.ScaleType
     */
    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    public boolean isRoundBackground() {
        return roundBackground;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mPaintFlagsDrawFilter);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (hasPressDownShade) {
                    if (mDrawable != null && mDrawable instanceof RoundDrawable) {
                        ((RoundDrawable) mDrawable).setPressDown(true);
                    } else if (mBackgroundDrawable != null
                            && mBackgroundDrawable instanceof RoundDrawable) {
                        ((RoundDrawable) mBackgroundDrawable).setPressDown(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (hasPressDownShade) {
                    if (mDrawable != null && mDrawable instanceof RoundDrawable) {
                        ((RoundDrawable) mDrawable).setPressDown(false);
                    } else if (mBackgroundDrawable != null
                            && mBackgroundDrawable instanceof RoundDrawable) {
                        ((RoundDrawable) mBackgroundDrawable).setPressDown(false);
                    }
                }
                break;
        }
        if (hasPressDownShade) {
            super.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
//		return true;
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        if (roundBackground && background != null) {
            mBackgroundDrawable = RoundDrawable.fromDrawable(background,
                    mCornerRadius, mBorderWidth, mBorderBgColor, mIsCircle);
            if (mBackgroundDrawable instanceof RoundDrawable) {
                ((RoundDrawable) mBackgroundDrawable)
                        .setScaleType(mScaleType);
                ((RoundDrawable) mBackgroundDrawable)
                        .setCornerRadius(mCornerRadius);
                ((RoundDrawable) mBackgroundDrawable)
                        .setBorderWidth(mBorderWidth);
                ((RoundDrawable) mBackgroundDrawable)
                        .setBorderColor(mBorderBgColor);
            }
        } else {
            mBackgroundDrawable = background;
        }
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    public void setBorderColor(int color) {
        if (mBorderColor == color) {
            return;
        }

        this.mBorderColor = color;
        if (mDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mDrawable).setBorderColor(color);
        }
        if (roundBackground && mBackgroundDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mBackgroundDrawable).setBorderColor(color);
        }
        if (mBorderWidth > 0) {
            invalidate();
        }
    }

    public void setBorderWidth(int width) {
        if (mBorderWidth == width) {
            return;
        }

        this.mBorderWidth = width;
        if (mDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mDrawable).setBorderWidth(width);
        }
        if (roundBackground && mBackgroundDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mBackgroundDrawable).setBorderWidth(width);
        }
        invalidate();
    }

    public void setCornerRadius(int radius) {
        if (mCornerRadius == radius) {
            return;
        }

        this.mCornerRadius = radius;
        if (mDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mDrawable).setCornerRadius(radius);
        }

        if(mDrawable instanceof FrameSequenceDrawable){

//            GifDrawableUtils.setCornerRadius((GifDrawable) mDrawable,radius);

            ((FrameSequenceDrawable) mDrawable).setRadius(radius,radius);
        }

        if (roundBackground && mBackgroundDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mBackgroundDrawable).setCornerRadius(radius);
        }
    }

    public void setHasPressDownShade(boolean hasPressDownShade) {
        this.hasPressDownShade = hasPressDownShade;
    }

    public void setImageBitmap(Bitmap bm) {

        if (bm != null) {
            if (!mUseCache) {
                mDrawable = new RoundDrawable(bm
                        , mCornerRadius, mBorderWidth, mBorderColor, mIsCircle);
            } else {
                mDrawable = RoundDrawableCache.getSingleInstance().getRoundDrawable(bm
                        , mCornerRadius, mBorderWidth, mBorderColor, mIsCircle);
            }
            ((RoundDrawable) mDrawable).setScaleType(mScaleType);
            ((RoundDrawable) mDrawable).setCornerRadius(mCornerRadius);
            ((RoundDrawable) mDrawable).setBorderWidth(mBorderWidth);
            ((RoundDrawable) mDrawable).setBorderColor(mBorderColor);
        } else {
            mDrawable = null;
        }
        super.setImageDrawable(mDrawable);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setImageResource(int resId) {
        if (resId > 0)
            try {
                setImageDrawable(getResources().getDrawable(resId));
            } catch (Exception e) { //可能有npe 或 ResourceNotFoundException
                e.printStackTrace();
            }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            if(drawable instanceof FrameSequenceDrawable){
                mDrawable = drawable;
            } else if(drawable instanceof NinePatchDrawable) {
                Bitmap bitmap;

                int width = drawable.getIntrinsicWidth();
                int height = drawable.getIntrinsicHeight();

                boolean measured = false;
                if(getWidth() > 0) {
                    width = getWidth();
                } else {
                    measure(0, 0);
                    measured = true;
                    if(getMeasuredWidth() > 1) {
                        width = getMeasuredWidth();
                    }
                }

                if(getHeight() > 0) {
                    height = getHeight();
                } else {
                    if(!measured) {
                        measure(0, 0);

                        if(getMeasuredHeight() > 1) {
                            height = getMeasuredHeight();
                        }
                    } else {
                        if(getMeasuredHeight() > 1) {
                            height = getMeasuredHeight();
                        }
                    }
                }

                if (width > 0 && height > 0) {
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                } else {
                    //适配纯色背景
                    bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                }

                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                mDrawable = new RoundDrawable(bitmap, mCornerRadius,
                        mBorderWidth, mBorderColor, mIsCircle);
            } else {
                mDrawable = RoundDrawable.fromDrawable(drawable, mCornerRadius,
                        mBorderWidth, mBorderColor, mIsCircle);
            }

            if (mDrawable instanceof RoundDrawable) {
                ((RoundDrawable) mDrawable).setScaleType(mScaleType);
                ((RoundDrawable) mDrawable).setCornerRadius(mCornerRadius);
                ((RoundDrawable) mDrawable).setBorderWidth(mBorderWidth);
                ((RoundDrawable) mDrawable).setBorderColor(mBorderColor);
            }

            if(mDrawable instanceof FrameSequenceDrawable){

//                ((GifDrawable) mDrawable).setCornerRadius(mCornerRadius);

//                ((GifDrawable) mDrawable).setTransform(new GifTransform(mCornerRadius,mScaleType));
                ((FrameSequenceDrawable) mDrawable).setRadius(mCornerRadius,mCornerRadius);
                ((FrameSequenceDrawable) mDrawable).setScaleType(mScaleType);
            }

        } else {
            mDrawable = null;
        }
        super.setImageDrawable(mDrawable);
    }

    public void setIsCircle(boolean isCircle) {
        mIsCircle = isCircle;
    }

    public void setRoundBackground(boolean roundBackground) {
        if (this.roundBackground == roundBackground) {
            return;
        }

        this.roundBackground = roundBackground;
        if (roundBackground) {
            if (mBackgroundDrawable instanceof RoundDrawable) {
                ((RoundDrawable) mBackgroundDrawable)
                        .setScaleType(mScaleType);
                ((RoundDrawable) mBackgroundDrawable)
                        .setCornerRadius(mCornerRadius);
                ((RoundDrawable) mBackgroundDrawable)
                        .setBorderWidth(mBorderWidth);
                ((RoundDrawable) mBackgroundDrawable)
                        .setBorderColor(mBorderBgColor);
            } else {
                setBackgroundDrawable(mBackgroundDrawable);
            }
        } else if (mBackgroundDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mBackgroundDrawable).setBorderWidth(0);
            ((RoundDrawable) mBackgroundDrawable).setCornerRadius(0);
        }

        invalidate();
    }

    /**
     * Controls how the image should be resized or moved to match the size of
     * this ImageView.
     *
     * @param scaleType The desired scaling mode.
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            if (mDrawable instanceof RoundDrawable
                    && ((RoundDrawable) mDrawable).getScaleType() != scaleType) {
                ((RoundDrawable) mDrawable).setScaleType(scaleType);
            }

            if(mDrawable instanceof FrameSequenceDrawable){

                ((FrameSequenceDrawable)mDrawable).setScaleType(scaleType);
            }

            if (mBackgroundDrawable instanceof RoundDrawable
                    && ((RoundDrawable) mBackgroundDrawable).getScaleType() != scaleType) {
                ((RoundDrawable) mBackgroundDrawable).setScaleType(scaleType);
            }
            setWillNotCacheDrawing(true);
            requestLayout();
            invalidate();
        }
    }

    public void setBorderColorWithOutInvalidate(int color) {
        if (mBorderColor == color) {
            return;
        }
        this.mBorderColor = color;
        if (mDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mDrawable).setBorderColor(color);
        }
        if (roundBackground && mBackgroundDrawable instanceof RoundDrawable) {
            ((RoundDrawable) mBackgroundDrawable).setBorderColor(color);
        }
    }

    public void setUseCache(boolean useCache) {
        this.mUseCache = useCache;
    }
}
