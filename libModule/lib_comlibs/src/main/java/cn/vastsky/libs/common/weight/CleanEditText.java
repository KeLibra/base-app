package cn.vastsky.libs.common.weight;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import cn.vastsky.libs.common.R;


/**
 *
 * Created by chenhongde on 2017/3/9.
 */

/**
 * @author: kezy
 * @create_time 2019/11/4
 * @description:
 *  带删除按钮的CleanEditText
 */
public class CleanEditText extends EditText implements View.OnFocusChangeListener {
    public static final int PHONE_NUM_INPUT_TYPE = 51;
    public static final int BANK_NUM_INPUT_TYPE = 61;
    int beforeTextLength = 0;//输入前文本长度
    int onTextLength = 0;//输入中文本长度
    boolean isChanged = false;//文字长度是否改变
    int location = 0;// 记录光标的位置
    private char[] tempChar;
    private StringBuffer buffer = new StringBuffer();
    int konggeNumberB = 0;
    /**
     * 焦点变化监听
     */
    public FocusChangeListener mFocusChangeListener;
    /**
     * edit是否有内容，button是否应该亮起监听
     */
    public HaveTextListener mHaveTextListener;
    /**
     * 文本变化监听
     */
    public TextChangeListener mTextChangeListener;
    /**
     * 监测文本是否符合要求
     */
    public RestrictingInputListener mRestrictingInputListener;
    /**
     * 设置输入类型
     */
    private int inputType;
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    //是否输入
    private boolean isRun = false;
    //输入的内容
    private String inputStr = "";

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 设置输入类型
     */
    public void setCardType(int inputType) {
        this.inputType = inputType;
    }

    /**
     * 焦点监听接口
     *
     * @param mFocusChangeListener
     */
    public void setMyFocusChangeListener(FocusChangeListener mFocusChangeListener) {
        this.mFocusChangeListener = mFocusChangeListener;
    }

    /**
     * 文本变化监听接口
     *
     * @param mTextChangeListener
     */
    public void setMyTextChangeListener(TextChangeListener mTextChangeListener) {
        this.mTextChangeListener = mTextChangeListener;
    }

    /**
     * 是否有输入内容的简听接口，用于控制button是否可点，控制工具类CleanEditTextEnableButtonUtil
     *
     * @param mHaveTextListener
     */
    public void setMyHaveTextListener(HaveTextListener mHaveTextListener) {
        this.mHaveTextListener = mHaveTextListener;
    }

    /**
     * 限制输入文本内容的接口
     *
     * @param mRestrictingInputListener
     */
    public void setMyRestrictingInputListener(RestrictingInputListener mRestrictingInputListener) {
        this.mRestrictingInputListener = mRestrictingInputListener;
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.cert_icon_circle_delete);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(new TextWatcher() {
            //当输入框里面内容发生变化的时候回调的方法

            @Override
            public void onTextChanged(CharSequence s, int start, int count,
                                      int after) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
                if (mRestrictingInputListener != null && count == 0) {
                    String editable = CleanEditText.this.getText().toString();
                    mRestrictingInputListener.observeText(editable);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if (inputType == BANK_NUM_INPUT_TYPE || inputType == PHONE_NUM_INPUT_TYPE) {
                    beforeTextLength = s.length();
                    if (buffer.length() > 0) {
                        buffer.delete(0, buffer.length());
                    }
                    konggeNumberB = 0;
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == ' ') {
                            konggeNumberB++;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mHaveTextListener != null) {
                    mHaveTextListener.haveText(s);
                }
                if (mTextChangeListener != null) {
                    mTextChangeListener.textChange(s);
                }
                if (hasFoucs) {
                    setClearIconVisible(getText().length() > 0);
                }
                if (inputType == BANK_NUM_INPUT_TYPE) {
                    formatBankCardNumber();
                }
                if (inputType == PHONE_NUM_INPUT_TYPE) {
                    formatPhoneNumber();
                }
            }
        });
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mFocusChangeListener != null) {
            mFocusChangeListener.focusChange(hasFocus);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 对外提供FocusChangeListener的监听回调接口
     */
    public interface FocusChangeListener {
        void focusChange(boolean hasFocus);
    }

    /**
     * 对外提供TextChangeListener的监听回调接口
     */
    public interface TextChangeListener {
        void textChange(Editable s);
    }

    /**
     * 对外提供Button是否要亮起的监听回调接口
     */
    public interface HaveTextListener {
        void haveText(Editable s);
    }

    /**
     * 对外提供EditText输入内容控制接口
     */
    public interface RestrictingInputListener {
        void observeText(String str);
    }

    /**
     * 格式化银行卡号
     */
    private void formatBankCardNumber() {
        if (isChanged) {
            location = this.getSelectionEnd();
            int index = 0;
            while (index < buffer.length()) {
                if (buffer.charAt(index) == ' ') {
                    buffer.deleteCharAt(index);
                } else {
                    index++;
                }
            }

            index = 0;
            int konggeNumberC = 0;
            while (index < buffer.length()) {
                if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                    buffer.insert(index, ' ');
                    konggeNumberC++;
                }
                index++;
            }

            if (konggeNumberC > konggeNumberB) {
                location += (konggeNumberC - konggeNumberB);
            }

            tempChar = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), tempChar, 0);
            String str = buffer.toString();
            if (location > str.length()) {
                location = str.length();
            } else if (location < 0) {
                location = 0;
            }

            this.setText(str);
            Editable etable = this.getText();
            // 超出限定位数时需要截断
            location = etable.length();
            Selection.setSelection(etable, location);
            isChanged = false;
        }
    }

    /**
     * 格式化手机号
     */
    private void formatPhoneNumber() {
        if (isChanged) {
            location = this.getSelectionEnd();
            int index = 0;
            while (index < buffer.length()) {
                if (buffer.charAt(index) == ' ') {
                    buffer.deleteCharAt(index);
                } else {
                    index++;
                }
            }

            index = 0;
            int konggeNumberC = 0;
            while (index < buffer.length()) {
                if ((index == 3 || index == 8)) {
                    buffer.insert(index, ' ');
                    konggeNumberC++;
                }
                index++;
            }

            if (konggeNumberC > konggeNumberB) {
                location += (konggeNumberC - konggeNumberB);
            }

            tempChar = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), tempChar, 0);
            String str = buffer.toString();
            if (location > str.length()) {
                location = str.length();
            } else if (location < 0) {
                location = 0;
            }

            this.setText(str);
            Editable etable = this.getText();
            location = etable.length();
            Selection.setSelection(etable, location);
            isChanged = false;
        }
    }
}