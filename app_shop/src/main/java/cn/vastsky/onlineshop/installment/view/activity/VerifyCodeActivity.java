package cn.vastsky.onlineshop.installment.view.activity;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.BindView;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.config.userinfo.LoginFacade;
import cn.vastsky.libs.common.config.userinfo.LoginListener;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseActivity;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.utils.SoftInputUtils;
import cn.vastsky.onlineshop.installment.common.weight.CountDownTextView;
import cn.vastsky.onlineshop.installment.common.weight.VerifyCodeEditView;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.request.UserTokenResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.AuthCodeResponse;
import cn.vastsky.onlineshop.installment.service.UserModel;

public class VerifyCodeActivity extends SimpleBaseActivity implements LoginListener {

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.et_verify_code)
    VerifyCodeEditView etVerifyCode;
    @BindView(R.id.tv_resend_code)
    CountDownTextView tvResendCode;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_verify_code;
    }

    public String phone;
    public String loginCallback;
    public String loginSuccToUrl;

    @Override
    protected void getIntentData() {
        phone = getIntent().getStringExtra("user_phone");
        loginCallback = getIntent().getStringExtra("loginCallback");
        loginSuccToUrl = getIntent().getStringExtra("loginSuccToUrl");

    }

    @Override
    protected void initView() {

        LoginFacade.registerLoginListener(this);
        initMsg();
        initListener();
    }

    private void initListener() {
        etVerifyCode.postDelayed(new Runnable() {
            @Override
            public void run() {
                SoftInputUtils.showInput(etVerifyCode.getEditText());
            }
        }, 300);

        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel.getAuthCode(phone, new ICallBack<AuthCodeResponse>() {
                    @Override
                    public void onSuccess(@Nullable AuthCodeResponse o) {
                        tvError.setText("");
                        etVerifyCode.clearPwd();
                        tvResendCode.start();

                        ToastUtil.show("验证码已发送");
                    }

                    @Override
                    public void onFailure(String err_msg) {
                        ToastUtil.show(err_msg);
                    }
                });
            }
        });
        //请求网络判断是
        etVerifyCode.setOnVerificationCompletedListener(new VerifyCodeEditView.OnVerificationCompletedListener() {
            @Override
            public void onCompleted(String code) {
                showLoading("");
                UserModel.doLogin(phone, code, new ICallBack<UserTokenResponse>() {
                    @Override
                    public void onSuccess(UserTokenResponse response) {
                        hideLoading();
                        LogUtils.d("----------------" + response);
                        if (response != null && !TextUtils.isEmpty(response.token)) {
                            ToastUtil.show("登录成功");
                            LoginFacade.setToken(response.token);
                            LoginFacade.setLogin(true);
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_LOGIN_SUCC));
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_LOGIN_CALLBACK, loginCallback));
                            LoginFacade.setPhone(phone);
                            LoginFacade.notifyLoginSucceed(phone, response.token, null);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String err_msg) {
                        hideLoading();
                        tvError.setText("* " + err_msg);
                        Spanned txt = Html.fromHtml("已向尾号  " + "<font color='#222222'>" + phoneStr + "</font>" + "  发送短信验证码");
                        tvMsg.setText(txt);
                    }
                });
            }
        });

        etVerifyCode.setOnInputChangedListener(new VerifyCodeEditView.OnInputChangedListener() {
            @Override
            public void inputChanged() {
                Spanned txt = Html.fromHtml("已向尾号  " + "<font color='#222222'>" + phoneStr + "</font>" + "  发送短信验证码<br/>请输入");
                tvMsg.setText(txt);
                tvError.setText("");
            }
        });
    }

    String phoneStr = "";

    private void initMsg() {

        if (!TextUtils.isEmpty(phone) && phone.length() > 4) {
            phoneStr = phone.substring(phone.length() - 4);
        } else {
            phoneStr = phone;
        }
        Spanned txt = Html.fromHtml("已向尾号  " + "<font color='#222222'>" + phoneStr + "</font>" + "  发送短信验证码<br/>请输入");
        tvMsg.setText(txt);
        tvResendCode.start();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tvResendCode != null) {
            tvResendCode.cancel();
        }
    }

    @Override
    protected void onBack() {
        finish();
    }

    @Override
    public void loginSucceed(String userId, String token, Object extend) {
        if (!TextUtils.isEmpty(loginSuccToUrl)) {
            PageRouter.resolve(VerifyCodeActivity.this, loginSuccToUrl);
        }
    }

    @Override
    public void loginFailed(String userId, Throwable e) {

    }

    @Override
    public void logoutSucceed(String userId) {

    }

    @Override
    public void logoutFailed(String usedId, Throwable e) {

    }
}
