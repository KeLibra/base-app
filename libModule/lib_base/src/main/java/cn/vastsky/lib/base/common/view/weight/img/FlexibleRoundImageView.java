package cn.vastsky.lib.base.common.view.weight.img;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import cn.vastsky.lib.base.R;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description  灵活可配置的圆角且可设置宽高比的ImageView
 */
public class FlexibleRoundImageView extends ImageView {

    private FlexibleRoundDrawable mFlexibleRoundDrawable;
    private Drawable mBackgroundDrawable;
    private int cornerRadius;
    private int corners = FlexibleRoundDrawable.CORNER_ALL;
    private boolean mIsSquare;
    private boolean mKeepScaleByHeight;

    private ScaleType mScaleType;

    /**
     * 宽高比例
     */
    private float mRatio = 0f;

    private NinePatchDrawable mNinePatchDrawable;

    public FlexibleRoundImageView(Context context) {
        super(context);
    }

    public FlexibleRoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleRoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexibleRoundImageView, defStyleAttr, 0);
        cornerRadius = (int) typedArray.getDimensionPixelSize(R.styleable.FlexibleRoundImageView_flexible_round_corner_radius, 0);
        corners = typedArray.getInt(R.styleable.FlexibleRoundImageView_flexible_round_corner, FlexibleRoundDrawable.CORNER_ALL);
        mIsSquare = typedArray.getBoolean(R.styleable.FlexibleRoundImageView_is_square, false);
        mRatio = typedArray.getFloat(R.styleable.FlexibleRoundImageView_ratio, 0f);
        mKeepScaleByHeight = typedArray.getBoolean(R.styleable.FlexibleRoundImageView_keep_scale_by_height, false);
        // 父类构造方法中调用setImageDrawable时，还没获取到👆这些属性值。所以再需要调一次，给Drawable更新一下。
        setImageDrawable(getDrawable());
        typedArray.recycle();
    }

    /**
     * 设置ImageView的宽高比
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mNinePatchDrawable = null;

        if (bm != null) {
            mFlexibleRoundDrawable = new FlexibleRoundDrawable(bm, cornerRadius, corners);
            mFlexibleRoundDrawable.setScaleType(mScaleType);
            setImageDrawable(mFlexibleRoundDrawable);
        } else {
            mFlexibleRoundDrawable = null;
            super.setImageDrawable(null);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(mNinePatchDrawable != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    setImageDrawable(mNinePatchDrawable);
                }
            });
        }
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        mNinePatchDrawable = null;

        if (drawable != null) {
            if (drawable instanceof FlexibleRoundDrawable) {
                mFlexibleRoundDrawable = (FlexibleRoundDrawable) drawable;
                mFlexibleRoundDrawable.setCornerRadius(cornerRadius);
                mFlexibleRoundDrawable.setCorners(corners);
            } else if(drawable instanceof NinePatchDrawable) {
                mNinePatchDrawable = (NinePatchDrawable) drawable;

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

                mFlexibleRoundDrawable = new FlexibleRoundDrawable(bitmap, cornerRadius, corners);
            } else {
                mFlexibleRoundDrawable = new FlexibleRoundDrawable(drawable, cornerRadius, corners);
            }
            mFlexibleRoundDrawable.setScaleType(mScaleType);
            super.setImageDrawable(mFlexibleRoundDrawable);
        } else{
            mFlexibleRoundDrawable = null;
            super.setImageDrawable(null);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void setImageResource(int resId) {
        mNinePatchDrawable = null;
        if (resId > 0) {
            setImageDrawable(getResources().getDrawable(resId));
        } else {
            super.setImageResource(resId);
        }
    }


    @Override
    public void setBackgroundDrawable(Drawable background) {
        mNinePatchDrawable = null;

        if (background != null && !(background instanceof FlexibleRoundDrawable)) {
            background = FlexibleRoundDrawable.createFlexibleRoundDrawable(background, cornerRadius, corners);
            ((FlexibleRoundDrawable) background).setScaleType(mScaleType);
        }
        this.mBackgroundDrawable = background;
        super.setBackgroundDrawable(background);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            if (mFlexibleRoundDrawable != null && mFlexibleRoundDrawable.getScaleType() != scaleType) {
                mFlexibleRoundDrawable.setScaleType(scaleType);
            }

            if (mBackgroundDrawable instanceof FlexibleRoundDrawable
                    && ((FlexibleRoundDrawable) mBackgroundDrawable).getScaleType() != scaleType) {
                ((FlexibleRoundDrawable) mBackgroundDrawable).setScaleType(scaleType);
            }

            // CENTER和CENTER_CROP会裁剪Drawable，导致圆角可能被裁掉，需要做一下处理
            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }
        }
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        if (mFlexibleRoundDrawable != null) {
            mFlexibleRoundDrawable.setCornerRadius(cornerRadius);
        }
    }

    public int getCorners() {
        return corners;
    }

    public void setCorners(int corners) {
        this.corners = corners;
        if (mFlexibleRoundDrawable != null) {
            mFlexibleRoundDrawable.setCorners(corners);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsSquare) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

            int childWidthSize = getMeasuredWidth();
            int childHeightSize = getMeasuredHeight();
            int size = mKeepScaleByHeight ? childHeightSize : childWidthSize;

            //宽高比例
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (mRatio != 0) {
                float height = width / mRatio;
                heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
