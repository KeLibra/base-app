package cn.vastsky.onlineshop.installment.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.lib.paymodule.alipay.AlipayCallBack;
import cn.vastsky.lib.paymodule.alipay.AlipayUtil;
import cn.vastsky.lib.paymodule.alipay.bean.PayResult;
import cn.vastsky.lib.paymodule.wxpay.WXPayUtil;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.libs.common.view.impl.IErrorPageView;
import cn.vastsky.libs.gdlocation.config.LocationConfig;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseActivity;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.model.BaseElement;
import cn.vastsky.onlineshop.installment.model.bean.response.OrderPayResponse;
import cn.vastsky.onlineshop.installment.service.PaymentModel;
import cn.vastsky.onlineshop.installment.service.TestModel;


public class TestActivity extends SimpleBaseActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_click_1)
    Button btnClick;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.btn_click_2)
    Button btnClick2;
    @BindView(R.id.ed_alipay_order)
    EditText edAlipayOrder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main_test;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mTitleView.getTextView().setText("测试页");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        LogUtils.d("====msg_location:  " + LocationConfig.getLocationAddress());
    }

    @Override
    protected boolean hasErrorPage() {
        return true;
    }

    @OnClick(R.id.btn_click_1)
    public void onViewClicked() {

        long orderLong = 0;
        try {
            orderLong = Long.parseLong(edAlipayOrder.getText().toString().trim());
        } catch (Exception e) {
            ToastUtil.show("订单号 为 数字");
            e.printStackTrace();
        }

        PaymentModel.getOrderPayInfo(orderLong, 1, new ICallBack<OrderPayResponse>() {
            @Override
            public void onSuccess(OrderPayResponse orderPayResponse) {

                AlipayUtil.getInstance().pay(TestActivity.this, orderPayResponse.orderInfo, true, new AlipayCallBack() {
                    @Override
                    public void callBack(PayResult resultSet) {
                        ToastUtil.show(resultSet.toString());
                    }
                });
            }

            @Override
            public void onFailure(String err_msg) {
                ToastUtil.show(err_msg);
            }
        });

    }


    @OnClick(R.id.btn_click_2)
    public void onClick() {

        long orderLong = 0;
        try {
            orderLong = Long.parseLong(edAlipayOrder.getText().toString().trim());
        } catch (Exception e) {
            ToastUtil.show("订单号 为 数字");
            e.printStackTrace();
        }

        PaymentModel.getOrderPayInfo(orderLong, 2, new ICallBack<OrderPayResponse>() {
            @Override
            public void onSuccess(OrderPayResponse orderPayResponse) {
                try {
                    JSONObject object = new JSONObject(orderPayResponse.orderInfo);
                    if (object != null && !object.has("retcode")) {
                        PayReq payReq = new PayReq();
                        payReq.appId = object.optString("appid"); //
                        payReq.partnerId = object.optString("partnerid");
                        payReq.prepayId = object.optString("prepay_id"); //
                        payReq.nonceStr = object.optString("nonce_str"); //
                        payReq.packageValue = object.optString("package");
                        payReq.timeStamp = object.optString("timestamp"); //
                        payReq.sign = object.optString("sign"); //

                        WXPayUtil.getInstance().pay(TestActivity.this, object.optString("appid"), payReq);
                    } else {
                        LogUtils.d("PAY_GET" + "返回错误 :" + object.getString("retmsg"));
                    }

                } catch (JSONException e) {
                    LogUtils.e("===== error: " + e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String err_msg) {
                ToastUtil.show(err_msg);
            }
        });

    }

//    public void faceAndOcrTest() {
//        Intent idIntent = new Intent(this, FmLoanIdCardCertActivity.class);
//        idIntent.putExtra("hasLiveness", true);
//        startActivity(idIntent);
//    }


    public void loadImageTest() {
        showLoading("正在加载中...");
        ImageLoadUtils.loadImage(this, "https://img2.3lian.com/2014/f6/173/d/50.jpg", ivImg,
                new RequestListener() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }
                });
    }

    public void testHttpQuery() {
        showLoading("正在加载中...");
        TestModel.getBottomIcon(new ICallBack<List<BaseElement>>() {
            @Override
            public void onSuccess(List<BaseElement> baseElements) {
                dismissProgressDialog();
                showContent();
                tvMsg.setText("请求成功： \n" + baseElements);
            }

            @Override
            public void onFailure(String err_msg) {

                dismissProgressDialog();

                tvMsg.setText("请求失败： \n" + err_msg);

                showErrorPage(0);

                mErrorPageView.setOnRefreshListener(new IErrorPageView.OnRefreshListener() {
                    @Override
                    public void onRefreshBtnClick() {

                        btnClick2.performClick();
                    }
                });
            }
        });
    }

}