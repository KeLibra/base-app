package cn.pingan.claim.app.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.kezy.libs.common.utils.LogUtils;
import cn.pingan.claim.app.R;
import cn.pingan.claim.app.base.common.net.ICallBack;
import cn.pingan.claim.app.base.common.utils.OkHttpUtils;
import cn.pingan.claim.app.base.view.SimpleBaseActivity;
import cn.pingan.claim.app.model.response.CameraBaseResponse;
import cn.pingan.claim.app.model.response.CameraProjectInfoResponse;
import cn.pingan.claim.app.model.response.CameraProjectListResponse;
import cn.pingan.claim.app.model.response.DeviceInfoResponse;
import cn.pingan.claim.app.service.CameraDeviceModel;

/**
 * @author: Kezy
 * @create_time 2020/1/26 0026  13:31
 * @copyright kezy
 * @description:
 */
public class CameraTestActivity extends SimpleBaseActivity {


    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_deviceinfo)
    Button btnDeviceinfo;
    @BindView(R.id.btn_newprj)
    Button btnNewprj;
    @BindView(R.id.btn_prjs)
    Button btnPrjs;
    @BindView(R.id.btn_prjinfo)
    Button btnPrjinfo;
    @BindView(R.id.btn_recordstate)
    Button btnRecordstate;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_snap)
    Button btnSnap;
    @BindView(R.id.btn_live)
    Button btnLive;

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_deviceinfo, R.id.btn_newprj, R.id.btn_prjs, R.id.btn_prjinfo, R.id.btn_recordstate, R.id.btn_record, R.id.btn_snap, R.id.btn_live})
    public void onViewClicked(View view) {
        showLoading("请求中...");
        switch (view.getId()) {
            case R.id.btn_deviceinfo:
                deviceInfo();
                break;
            case R.id.btn_newprj:
                newProject();
                break;
            case R.id.btn_prjs:
                projectList();
                break;
            case R.id.btn_prjinfo:
                projectDetail();
                break;
            case R.id.btn_recordstate:
                deviceStatus();
                break;
            case R.id.btn_record:
                record();
                break;
            case R.id.btn_snap:
                snap();
                break;
            case R.id.btn_live:
                live();
                break;
        }
    }

    private void deviceInfo() {
        CameraDeviceModel.getDeviceInfo(new ICallBack<DeviceInfoResponse>() {
            @Override
            public void onSuccess(DeviceInfoResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("deviceInfo : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("deviceInfo 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void newProject() {
        CameraDeviceModel.newProject("Cam2020002", "PA111111", new ICallBack<CameraBaseResponse>() {
            @Override
            public void onSuccess(CameraBaseResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("newProject : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("newProject 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void projectList() {
        CameraDeviceModel.getProjectList("Cam2020002", new ICallBack<CameraProjectListResponse>() {
            @Override
            public void onSuccess(CameraProjectListResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("projectList : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("projectList 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void projectDetail() {
        CameraDeviceModel.getProjectInfo("Cam2020002", "PA111111", new ICallBack<CameraProjectInfoResponse>() {
            @Override
            public void onSuccess(CameraProjectInfoResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("projectDetail : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("projectDetail 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void deviceStatus() {
        CameraDeviceModel.getCameraStatus(new ICallBack<CameraBaseResponse>() {
            @Override
            public void onSuccess(CameraBaseResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("deviceStatus : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("deviceStatus 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void record() {
        CameraDeviceModel.recordCamera("Cam2020002", "PA111111", new ICallBack<CameraBaseResponse>() {
            @Override
            public void onSuccess(CameraBaseResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("record : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("record 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }

    private void snap() {
//        CameraDeviceModel.snapCamera("Cam2020002", "PA111111", new ICallBack<CameraBaseResponse>() {
//            @Override
//            public void onSuccess(CameraBaseResponse deviceInfoResponse) {
//                hideLoading();
//                tvMsg.setText("record : " + deviceInfoResponse.toString());
//                tvMsg.setTextColor(Color.BLACK);
//            }
//
//            @Override
//            public void onFailure(String err_msg) {
//                hideLoading();
//                tvMsg.setText("record 错误： " + err_msg.toString());
//                tvMsg.setTextColor(Color.RED);
//            }
//        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                String response = OkHttpUtils.getInstance().doPostJson("http://192.168.1.106:8080//app/snap", "{\"DevSn\":\"Cam2020002\",\"PrjNo\": PA111111}");
                LogUtils.e("------------------ ---------- response: " + response.toString());
            }
        }).start();

//        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
//        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//        formBody.add("DevSn", "Cam2020002");//传递键值对参数
//        formBody.add("PrjNo", "PA111111");//传递键值对参数
//        Request request = new Request.Builder()//创建Request 对象。
//                .url("http://192.168.1.106:8080//app/snap")
//                .post(formBody.build())//传递请求体
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                hideLoading();
//                LogUtils.e("------------------ ---------- response: " + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                hideLoading();
//                LogUtils.e("------------------ ---------- response: " + e.toString());
//            }
//        });

    }

    private void live() {
        CameraDeviceModel.liveCamera("Cam2020002", "PA111111", new ICallBack<CameraBaseResponse>() {
            @Override
            public void onSuccess(CameraBaseResponse deviceInfoResponse) {
                hideLoading();
                tvMsg.setText("record : " + deviceInfoResponse.toString());
                tvMsg.setTextColor(Color.BLACK);
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                tvMsg.setText("record 错误： " + err_msg.toString());
                tvMsg.setTextColor(Color.RED);
            }
        });
    }


    @Override
    protected void onBack() {
        super.onBack();
        finish();
    }
}
