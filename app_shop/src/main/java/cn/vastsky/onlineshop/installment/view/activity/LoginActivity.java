package cn.vastsky.onlineshop.installment.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.store.Store;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.base.LibConstants;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseActivity;
import cn.vastsky.onlineshop.installment.common.VsConstants;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.utils.StringUtil;
import cn.vastsky.onlineshop.installment.common.weight.PhoneEditText;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.response.AuthCodeResponse;
import cn.vastsky.onlineshop.installment.service.UserModel;

public class LoginActivity extends SimpleBaseActivity {

    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @BindView(R.id.et_login_phone)
    PhoneEditText etLoginPhone;
    @BindView(R.id.tv_login_agreement)
    TextView tvLoginAgreement;
    @BindView(R.id.tv_btn_next)
    TextView tvBtnNext;
    @BindView(R.id.tv_slogo)
    TextView tvSlogo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    String loginCallback;

    String loginSuccToUrl;


    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loginCallback = bundle.getString("loginCallback");
            loginSuccToUrl = bundle.getString(LibConstants.Key.KEY_LOGIN_SUCC_CALLBACK_URL);
        }
    }

    @Override
    protected void initView() {

        fmTitleView.setImageBackIconRes(R.drawable.lib_icon_close);
        fmTitleView.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dofinish();
            }
        });
        registerEventBus();

//        String str = "<font color='#5DB7FF'>《" + "便利分期用户服务协议" + "》</font>";
//        tvLoginAgreement.setText(Html.fromHtml(str));

        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && s.toString().replaceAll(" ", "").length() == 11) {
                    tvBtnNext.setEnabled(true);
                } else {
                    tvBtnNext.setEnabled(false);
                }
            }
        });

        tvLoginAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(LoginActivity.this, loginAgreeUrl);
            }
        });

        if (AppConfigLib.isCkFlag()) {
            tvSlogo.setVisibility(View.GONE);
        } else {
            tvSlogo.setVisibility(View.VISIBLE);
        }

    }

    private String loginAgreeUrl = "";
    private String privacyPolicyDocUrl = "";

    @Override
    protected void loadData() {
        loginAgreeUrl = Store.sp(AppConfigLib.getContext()).getString(VsConstants.Key.KEY_REGISTER_DOC_URL, "");
        privacyPolicyDocUrl = Store.sp(AppConfigLib.getContext()).getString(VsConstants.Key.KEY_PRIVACY_DOC_URL, "");

        if (!TextUtils.isEmpty(loginAgreeUrl) || !TextUtils.isEmpty(privacyPolicyDocUrl)) {
            initAgreement();
        }
    }

    private void initAgreement() {

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append("下一步即表示同意");
        int index = stringBuilder.length();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_9b9b9b));
        stringBuilder.setSpan(colorSpan, 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String userStr = "";
        if (!TextUtils.isEmpty(loginAgreeUrl)) {
            userStr = "《便利分期用户服务协议》";
        }
        String privateStr = "";
        if (!TextUtils.isEmpty(privacyPolicyDocUrl)) {
            privateStr = "《用户隐私声明》";
        }

        stringBuilder.append(userStr);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                PageRouter.resolve(getContext(), loginAgreeUrl);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        };
        stringBuilder.setSpan(clickableSpan1, index, index + userStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        stringBuilder.append(privateStr);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                PageRouter.resolve(getContext(), privacyPolicyDocUrl);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //去掉可点击文字的下划线
                ds.setUnderlineText(false);
            }
        };
        stringBuilder.setSpan(clickableSpan2, index + userStr.length(), index + userStr.length() + privateStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置此方法后，点击事件才能生效
        tvLoginAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvLoginAgreement.setText(stringBuilder);
    }

    private String phone;

    @OnClick(R.id.tv_btn_next)
    public void onClick() {
        phone = etLoginPhone.getText().toString().replaceAll(" ", "").trim();
        if (StringUtil.isPhoneNumber(phone)) {
            sendSmsCode(phone);
        } else {
            ToastUtil.show("请输入正确的手机号");
        }
    }

    private void sendSmsCode(String phone) {
        showLoading("");
        UserModel.getAuthCode(phone, new ICallBack<AuthCodeResponse>() {
            @Override
            public void onSuccess(AuthCodeResponse s) {
                hideLoading();
                LogUtils.d("-------------------------- s: " + s);
                Intent intent = new Intent(mActivity, VerifyCodeActivity.class);
                intent.putExtra("user_phone", phone);
                intent.putExtra("loginCallback", loginCallback);
                intent.putExtra("loginSuccToUrl", loginSuccToUrl);

                startActivity(intent);
                ToastUtil.show("验证码已发送");
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    /**
     * 响应 eventbus 消息， 在主线程处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseEventBusMainThread(MessageEvent event) {
        if (MessageEvent.TYPE_LOGIN_SUCC.equals(event.eventType)) {
            LogUtils.d("---------------------- 登陆成功了 ");
            LoginActivity.this.finish();
        }
    }

    @Override
    protected void onBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
