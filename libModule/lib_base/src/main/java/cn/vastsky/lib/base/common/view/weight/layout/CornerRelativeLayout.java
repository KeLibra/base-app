package cn.vastsky.lib.base.common.view.weight.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.vastsky.lib.base.R;
import cn.vastsky.lib.utils.ViewUtils;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description  支持不规则裁切边角的layout
 */
public class CornerRelativeLayout extends RelativeLayout {
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;

    private float mCornerRadius = ViewUtils.dip2px(getContext(), 4);
    private Path mRoundPath;
    private RectF mRectF;
    private float[] mRadii = new float[8];
    private int mRoundCorners = CORNER_ALL;

    public CornerRelativeLayout(Context context) {
        this(context, null);
    }

    public CornerRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerRelativeLayout, defStyleAttr, 0);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.FlexibleRoundImageView_flexible_round_corner_radius, 0);
        mRoundCorners = typedArray.getInt(R.styleable.FlexibleRoundImageView_flexible_round_corner, CORNER_ALL);
        typedArray.recycle();
        init();
    }

    private void init() {
        mRoundPath = new Path();
        mRectF = new RectF();
        updateRadii();
    }

    private void updateRadii() {
        for (int i = 0; i < mRadii.length; i++) {
            mRadii[i] = 0;
        }
        if ((mRoundCorners & CORNER_TOP_LEFT) != 0) {
            mRadii[0] = mCornerRadius;
            mRadii[1] = mCornerRadius;
        }
        if ((mRoundCorners & CORNER_TOP_RIGHT) != 0) {
            mRadii[2] = mCornerRadius;
            mRadii[3] = mCornerRadius;
        }
        if ((mRoundCorners & CORNER_BOTTOM_RIGHT) != 0) {
            mRadii[4] = mCornerRadius;
            mRadii[5] = mCornerRadius;
        }
        if ((mRoundCorners & CORNER_BOTTOM_LEFT) != 0) {
            mRadii[6] = mCornerRadius;
            mRadii[7] = mCornerRadius;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG|Paint.ANTI_ALIAS_FLAG));
        mRoundPath.rewind();
        mRectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        mRoundPath.addRoundRect(mRectF, mRadii, Path.Direction.CW);
        canvas.clipPath(mRoundPath);
        super.dispatchDraw(canvas);
    }

    public void setCornerRadius(float cornerRadius){
        this.mCornerRadius = cornerRadius;
        updateRadii();
        postInvalidate();
    }

    public void setRoundCorners(int roundCorners) {
        mRoundCorners = roundCorners;
        updateRadii();
        postInvalidate();
    }
}
