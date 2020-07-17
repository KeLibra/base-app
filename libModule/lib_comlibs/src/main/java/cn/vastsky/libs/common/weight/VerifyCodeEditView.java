package cn.vastsky.libs.common.weight;


import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.vastsky.libs.common.R;
import cn.vastsky.libs.common.utils.SoftInputUtils;

public class VerifyCodeEditView extends LinearLayout {

    private static final int LENGTH = 4;
    private Context mContext;
    private EditText mRealInputEdt;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private TextView mBlinkTv;
    private LinearLayout mVerficationLayout;
    private OnVerificationCompletedListener mOnVerificationCompletedListener;
    private OnInputChangedListener mOnInputChangedListener;
    private ValueAnimator mBlinkValueAnimator;

    public VerifyCodeEditView(Context context) {
        super(context);
        this.initLayout(context);
    }

    public VerifyCodeEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initLayout(context);
    }

    public void setEditable(boolean editable) {
        if (editable) {
            this.mVerficationLayout.setClickable(true);
            this.mText1.setBackgroundResource(R.drawable.underline_yellow_bg);
            this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
            this.mText3.setBackgroundResource(R.drawable.underline_grey_bg);
            this.mText4.setBackgroundResource(R.drawable.underline_grey_bg);
            this.startAnimation();
        } else {
            this.mVerficationLayout.setClickable(false);
            this.mText1.setBackgroundResource(R.drawable.underline_grey_bg);
            this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
            this.mText3.setBackgroundResource(R.drawable.underline_grey_bg);
            this.mText4.setBackgroundResource(R.drawable.underline_grey_bg);
            if (this.mBlinkValueAnimator != null) {
                this.mBlinkValueAnimator.cancel();
            }
        }

    }

    private void initLayout(Context context) {
        this.mContext = context;
        View mView = LayoutInflater.from(context).inflate(R.layout.layout_verification_code, (ViewGroup) null);
        this.addView(mView);
        this.mRealInputEdt = mView.findViewById(R.id.et_verification_code_input);
        this.mRealInputEdt.setVisibility(VISIBLE);
        this.mText1 = (TextView) mView.findViewById(R.id.tv_code1);
        this.mText2 = (TextView) mView.findViewById(R.id.tv_code2);
        this.mText3 = (TextView) mView.findViewById(R.id.tv_code3);
        this.mText4 = (TextView) mView.findViewById(R.id.tv_code4);
        this.mVerficationLayout = (LinearLayout) mView.findViewById(R.id.ll_verification_code);
        this.mRealInputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    int strLength = charSequence.toString().length();
                    String inputNo = "";
                    if (charSequence.length() == 0) {
                        inputNo = "";
                    }

                    if (strLength >= 1) {
                        inputNo = charSequence.toString().substring(strLength - 1, strLength);
                    }

                    VerifyCodeEditView.this.showTextView(strLength, inputNo);
                    if (VerifyCodeEditView.this.mOnInputChangedListener != null) {
                        VerifyCodeEditView.this.mOnInputChangedListener.inputChanged();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        this.mVerficationLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftInputUtils.showInput(mRealInputEdt);
            }
        });
        this.startAnimation();
    }

    private void startAnimation() {
        this.mBlinkTv = this.mText1;
        this.mBlinkTv.setTextColor(this.mContext.getResources().getColor(R.color.colorPrimary));
        this.mBlinkValueAnimator = ValueAnimator.ofInt(new int[]{0, 2});
        this.mBlinkValueAnimator.setDuration(1000L);
        this.mBlinkValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = ((Integer) animation.getAnimatedValue()).intValue();
                if (VerifyCodeEditView.this.mBlinkTv != null) {
                    if (currentValue % 2 == 0) {
                        VerifyCodeEditView.this.mBlinkTv.setText("");
                    } else {
                        VerifyCodeEditView.this.mBlinkTv.setText("|  ");
                    }

                }
            }
        });
        this.mBlinkValueAnimator.setRepeatCount(-1);
        this.mBlinkValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mBlinkValueAnimator != null) {
            this.mBlinkValueAnimator.cancel();
        }

    }

    private void showTextView(int strLength, String inputValue) {
        switch (strLength) {
            case 1:
                this.resetBlinkTv(this.mText2);
                this.mText1.setText(inputValue);
                this.mText2.setText("");
                this.mText3.setText("");
                this.mText4.setText("");
                this.mText1.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText2.setBackgroundResource(R.drawable.underline_yellow_bg);
                this.mText3.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText4.setBackgroundResource(R.drawable.underline_grey_bg);
                break;
            case 2:
                this.resetBlinkTv(this.mText3);
                this.mText2.setText(inputValue);
                this.mText3.setText("");
                this.mText4.setText("");
                this.mText1.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText3.setBackgroundResource(R.drawable.underline_yellow_bg);
                this.mText4.setBackgroundResource(R.drawable.underline_grey_bg);
                break;
            case 3:
                this.resetBlinkTv(this.mText4);
                this.mText3.setText(inputValue);
                this.mText4.setText("");
                this.mText1.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText3.setBackgroundResource(R.drawable.underline_grey_bg);
                this.mText4.setBackgroundResource(R.drawable.underline_yellow_bg);
                break;
            case 4:
                this.mBlinkTv = null;
                this.mText4.setTextColor(this.mContext.getResources().getColor(R.color.color_333333));
                this.mText4.setText(inputValue);
                this.allFocusUnable();
                SoftInputUtils.hideInput((Activity) getContext());
                if (this.mOnVerificationCompletedListener != null) {
                    this.mOnVerificationCompletedListener.onCompleted(this.getStringPwd());
                }
                break;
            default:
                this.resetBlinkTv(this.mText1);
                this.mText1.setText("");
                this.mText1.setBackgroundResource(R.drawable.underline_yellow_bg);
                this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
        }

    }

    private void resetBlinkTv(TextView tv) {
        if (this.mBlinkTv != null) {
            this.mBlinkTv.setTextColor(this.mContext.getResources().getColor(R.color.color_333333));
            this.mBlinkTv.setText("");
        }

        this.mBlinkTv = tv;
        this.mBlinkTv.setTextColor(this.mContext.getResources().getColor(R.color.color_107fef));
    }

    private void allFocusUnable() {
        this.mText2.setBackgroundResource(R.drawable.underline_grey_bg);
        this.mText3.setBackgroundResource(R.drawable.underline_grey_bg);
        this.mText4.setBackgroundResource(R.drawable.underline_grey_bg);
    }

    public String getStringPwd() {
        return this.mRealInputEdt.getText().toString();
    }

    public void setOnVerificationCompletedListener(VerifyCodeEditView.OnVerificationCompletedListener mOnVerificationCompletedListener) {
        this.mOnVerificationCompletedListener = mOnVerificationCompletedListener;
    }

    public void setOnInputChangedListener(VerifyCodeEditView.OnInputChangedListener mOnInputChangedListener) {
        this.mOnInputChangedListener = mOnInputChangedListener;
    }

    public void clearPwd() {
        this.resetBlinkTv(this.mText1);
        this.mRealInputEdt.setText("");
        this.mText1.setText("");
        this.mText2.setText("");
        this.mText3.setText("");
        this.mText4.setText("");
        this.allFocusUnable();
    }

    public EditText getEditText() {
        return this.mRealInputEdt;
    }

    public interface OnInputChangedListener {
        void inputChanged();
    }

    public interface OnVerificationCompletedListener {
        void onCompleted(String var1);
    }
}
