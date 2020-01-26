package cn.pingan.claim.app.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import cn.jzvd.Jzvd;
import cn.jzvd.view.MyJzvdStd;
import cn.kezy.libs.common.utils.ToastUtil;
import cn.pingan.claim.app.R;
import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.base.common.net.config.UrlConfig;
import cn.pingan.claim.app.model.bean.InitConfigResponse;
import cn.pingan.claim.app.service.TestModel;

public class MainActivity extends AppCompatActivity {


    private Button button, button2;
    private TextView msg;

    private EditText et_url; //

    MyJzvdStd myJzvdStd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myJzvdStd = findViewById(R.id.jz_video);
        myJzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大");
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(myJzvdStd.thumbImageView);


        button = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        msg = findViewById(R.id.msg);

        et_url = findViewById(R.id.et_url);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.setText("");
                getData();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getData2();
                String hostName = et_url.getText().toString().trim();
                if (TextUtils.isEmpty(hostName)) {
                    ToastUtil.show("请输入 IP和端口号");
                } else {
                    UrlConfig.CameraUrl = "http://" + hostName + "/";
                }
                startActivity(new Intent(MainActivity.this, CameraTestActivity.class));
            }
        });
    }

    private void getData() {
        TestModel.getInitConfig(new ICallBack<InitConfigResponse>() {
            @Override
            public void onSuccess(InitConfigResponse initConfigResponse) {
                msg.setText("1111 : " + initConfigResponse.toString());
                msg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                msg.setText("1111 错误： " + err_msg.toString());
                msg.setTextColor(Color.RED);
            }
        });
    }

    private void getData2() {

//        UrlConfig.CameraUrl = "";

//        CameraDeviceModel.getDeviceInfo(new ICallBack<String>() {
//            @Override
//            public void onSuccess(String deviceInfoResponse) {
//                msg.setText("2222 : " + deviceInfoResponse.toString());
//                msg.setTextColor(Color.BLACK);
//            }
//
//            @Override
//            public void onFailure(String err_msg) {
//                msg.setText("2222 错误： " + err_msg.toString());
//                msg.setTextColor(Color.RED);
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myJzvdStd.onStatePause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
