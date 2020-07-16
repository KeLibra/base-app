package cn.vastsky.onlineshop.installment.common.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class CustomViewPager extends ViewPager {

    private float mDownY;
    private float mDownX;

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean touch = super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mDownX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //左右滑动
                if (Math.abs(ev.getY() - mDownY) - Math.abs(ev.getX() - mDownX) > 0) {
                    touch = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return touch;
    }
}

