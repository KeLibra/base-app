package cn.pingan.claim.app.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.pingan.claim.app.R;
import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.model.bean.InitConfigResponse;
import cn.pingan.claim.app.service.TestModel;

public class MainActivity extends AppCompatActivity {


    private Button button, button2;
    private TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
