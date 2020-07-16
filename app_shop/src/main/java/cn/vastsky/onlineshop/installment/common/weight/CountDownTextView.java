package cn.vastsky.onlineshop.installment.common.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;
import cn.vastsky.onlineshop.installment.R;


public class CountDownTextView extends AppCompatTextView {

    private Context context;
    private long times;
    private long interval;
    private boolean isFinish;
    private CountDownTimer timer;

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs, defStyleAttr);
        isFinish = true;
        setGravity(Gravity.CENTER);
        normalBackground();
        timer = new CountDownTimer(times, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                isFinish = false;
                setEnabled(false);
                long left = Math.round((double) millisUntilFinished / 1000) - 1;
                setText(Html.fromHtml("<font color='#333333'>" + left + "S</font>" + "后可重新发送验证码"));
                setTextColor(Color.parseColor("#666666"));
            }

            @Override
            public void onFinish() {
                isFinish = true;
                setEnabled(true);
                setText("获取验证码");
                setTextColor(Color.parseColor("#5DB7FF"));
            }
        };
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownTextView, defStyleAttr, 0);
        //设置默认时长
        times = (long) typedArray.getInt(R.styleable.CountDownTextView_times, 60000);
        //设置默认间隔时长
        interval = (long) typedArray.getInt(R.styleable.CountDownTextView_interval, 1000);
        typedArray.recycle();
    }

    private void normalBackground() {
        setText("获取验证码");
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void cancel() {
        timer.cancel();
    }

    public void start() {
        timer.start();
    }
}
