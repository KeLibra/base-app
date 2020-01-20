package cn.pingan.claim.app.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import cn.jzvd.Jzvd;
import cn.jzvd.view.MyJzvdStd;
import cn.pingan.claim.app.R;
import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.model.bean.InitConfigResponse;
import cn.pingan.claim.app.service.TestModel;

public class MainActivity extends AppCompatActivity {


    private Button button, button2;
    private TextView msg;

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData2();
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
        TestModel.getInitConfig2(new ICallBack<InitConfigResponse>() {
            @Override
            public void onSuccess(InitConfigResponse initConfigResponse) {
                msg.setText("2222 : " + initConfigResponse.toString());
                msg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                msg.setText("2222 错误： " + err_msg.toString());
                msg.setTextColor(Color.RED);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        myJzvdStd.onStatePause();
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
