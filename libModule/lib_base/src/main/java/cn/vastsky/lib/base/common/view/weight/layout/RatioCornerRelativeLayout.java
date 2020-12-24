package cn.vastsky.lib.base.common.view.weight.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import cn.vastsky.lib.base.common.view.weight.layout.CornerRelativeLayout;

/**
 * @Author Kezy
 * @Time 12/24/20
 * @Description 支持宽高比并且支持不规则裁切边角的layout
 */
public class RatioCornerRelativeLayout extends CornerRelativeLayout {
    /**
     * 宽高比例
     */
    private float mRatio = 0f;

    public RatioCornerRelativeLayout(Context context) {
        super(context);
    }

    public RatioCornerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioCornerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRatio(float ratio) {
        if(mRatio == ratio) {
            return;
        }
        mRatio = ratio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio != 0) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams != null) {
                if (layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT &&
                        layoutParams.height >= ViewGroup.LayoutParams.MATCH_PARENT) {
                    int height = MeasureSpec.getSize(heightMeasureSpec);
                    float width = height * mRatio;
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) width,
                            MeasureSpec.EXACTLY);
                } else {
                    int width = MeasureSpec.getSize(widthMeasureSpec);
                    float height = width / mRatio;
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height,
                            MeasureSpec.EXACTLY);
                }
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
