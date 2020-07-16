package cn.vastsky.onlineshop.installment.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.megvii.idcardquality.IDCardQualityLicenseManager;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.megvii.ocr.common.SerializableHashMap;
import com.megvii.ocr.idcardlib.IDCardScanActivity;
import com.megvii.ocr.idcardlib.util.Util;
import com.megvii.ocr.livenesslib.LivenessActivity;
import com.pingan.iobs.sdk.repkg.org.apache.commons.codec.binary.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.IBaseActivity;
import cn.vastsky.onlineshop.installment.contract.ILoanIdCardCertContract;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.OcrCertStutasResponse;
import cn.vastsky.onlineshop.installment.presenter.LoanIdCardCertPresenter;
import cn.vastsky.onlineshop.installment.router.CertRouterUtils;

import static android.os.Build.VERSION_CODES.M;

/**
 * 身份证认证
 * Created by airal on 2018/8/20.
 */

public class LoanIdCardCertActivity
        extends IBaseActivity<ILoanIdCardCertContract.ILoanIdCardCertContractViewPresenter>
        implements ILoanIdCardCertContract.ILoanIdCardCertContractView {

    @BindView(R.id.iv_cert_front_icon)
    ImageView ivCertFrontIcon;
    @BindView(R.id.ll_cert_front)
    LinearLayout llCertFront;
    @BindView(R.id.iv_cert_back_icon)
    ImageView ivCertBackIcon;
    @BindView(R.id.ll_cert_back)
    LinearLayout llCertBack;
    @BindView(R.id.iv_cert_face_icon)
    ImageView ivCertFaceIcon;
    @BindView(R.id.ll_cert_face)
    LinearLayout llCertFace;
    @BindView(R.id.tv_cert_btn)
    TextView tvCertBtn;
    @BindView(R.id.ll_root)
    RelativeLayout llRoot;


    private static final int PAGE_INTO_LIVE = 100;
    private static final String FACE_RECOGNITION_RETURN_IMG_MAP = "map";
    private static final String FACE_RECOGNITION_BEST_IMG = "image_best";
    private static final String FACE_RECOGNITION_ENV = "image_env";
    private static final String FACE_RECOGNITION_ACTION1 = "image_action1";
    private static final String FACE_RECOGNITION_ACTION2 = "image_action2";
    private static final String FACE_RECOGNITION_ACTION3 = "image_action3";
    private static final String FACE_RECOGNITION_KEY = "delta";
    private static final String FACE_RECOGNITION_RESULT = "result";
    private static final String FACE_RECOGNITION_RESULT_CODE = "resultcode";

    public static final int INTO_ID_CARD_SCAN = 99;
    public static final String ID_CARD_IMG = "idcardImg";

    boolean isVertical = false;

    private MediaPlayer mMediaPlayer = null;

    private boolean isFrontOcrVerify = false;  // 身份证 人脸正面认证
    private boolean isBackOcrVerify = false; // 身份证  国徽反面认证
    private boolean isFaceVerify = false;  // 活体 人脸认证

    private String orderSn;
    private int instProductId;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_certification_id_card;
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        orderSn = getIntent().getStringExtra("orderSn");
        instProductId = getIntent().getIntExtra("instProductId", -1);
    }

    @Override
    protected ILoanIdCardCertContract.ILoanIdCardCertContractViewPresenter getPresenter() {
        return new LoanIdCardCertPresenter(this);
    }

    @Override
    protected void initView() {

        mTitleView.getTextView().setText("身份认证");
        //联网授权
        netWorkWarranty();
    }

    @Override
    protected void loadData() {
        presenter.getOcrStatus();
        presenter.getFaceStatus(orderSn);
    }

    /**
     * 联网授权
     */
    private void netWorkWarranty() {
        final String uuid = Util.getUUIDString(this.getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(LoanIdCardCertActivity.this);
                //身份证授权
                IDCardQualityLicenseManager idCardLicenseManager = new IDCardQualityLicenseManager(
                        LoanIdCardCertActivity.this);
                manager.registerLicenseManager(idCardLicenseManager);
                if (idCardLicenseManager.checkCachedLicense() > 0) {
                    //授权成功
                    LogUtils.d("ocr", "身份证授权成功");
                } else {
                    //授权失败
                    LogUtils.d("ocr", "身份证授权失败");
                }
                //人脸授权
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(
                        LoanIdCardCertActivity.this);
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork(uuid);
                if (licenseManager.checkCachedLicense() > 0) {
                    //授权成功
                    LogUtils.d("ocr", "人脸授权成功");
                } else {
                    //授权失败
                    LogUtils.d("ocr", "人脸授权失败");
                }
            }
        }).start();
    }


    int mSide = 0;

    private void requestCameraPerm(int side) {
        mSide = side;
        if (Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        EXTERNAL_STORAGE_REQ_CAMERA_CODE);
            } else {
                enterNextPage(side);
            }
        } else {
            enterNextPage(side);
        }
    }

    private void enterNextPage(int side) {
        if (side != -1) {
            Intent intent = new Intent(this, IDCardScanActivity.class);
            intent.putExtra("side", side);
            intent.putExtra("isvertical", isVertical);
            startActivityForResult(intent, INTO_ID_CARD_SCAN);
        } else {
            startActivityForResult(new Intent(this, LivenessActivity.class), PAGE_INTO_LIVENESS);
        }
    }

    public static final int EXTERNAL_STORAGE_REQ_CAMERA_CODE = 10;
    private static final int PAGE_INTO_LIVENESS = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        /**
         * 听云bug： java.lang.ArrayIndexOutOfBoundsException: length=0; index=0
         */
        if (requestCode == EXTERNAL_STORAGE_REQ_CAMERA_CODE) {
            if (grantResults == null || grantResults.length <= 0 ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {// Permission Granted
                Util.showToast(this, "获取相机权限失败");
            } else {
                enterNextPage(mSide);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTO_ID_CARD_SCAN && resultCode == RESULT_OK) {
            byte[] idCardImgData = data.getByteArrayExtra(ID_CARD_IMG);
            int side = data.getIntExtra("side", -1);
            if (side == 0 || side == 1) {
                presenter.doOcrVerify(side + 1, Base64.encodeBase64String(idCardImgData));
                LogUtils.d("====msg_face" + " ----- onActivityResult ---- side == 0 || side == 1 ---" + Base64.encodeBase64String(idCardImgData));
            }
        } else if (requestCode == PAGE_INTO_LIVE && resultCode == RESULT_OK) {
            String resultOBJ = data.getStringExtra(FACE_RECOGNITION_RESULT);
            Log.e("====msg_face", " ----- onActivityResult ---- resultOBJ ---" + resultOBJ);

            try {
                JSONObject result = new JSONObject(resultOBJ);
                int resID = result.getInt(FACE_RECOGNITION_RESULT_CODE);

                Log.e("====msg_face", " ----- onActivityResult ---- FACE_RECOGNITION_RESULT_CODE---" + FACE_RECOGNITION_RESULT_CODE);
                Log.e("====msg_face", " ----- onActivityResult ---- resID---" + resID);
                Log.e("====msg_face", " ----- onActivityResult ---- R.string---" + R.string.verify_success);
                if (resID == R.string.verify_success) {
                    doPlay(R.raw.meglive_success);
                    showResult(data);
                } else if (resID == R.string.liveness_detection_failed_not_video) {
                    doPlay(R.raw.meglive_failed);
                } else if (resID == R.string.liveness_detection_failed_timeout) {
                    doPlay(R.raw.meglive_failed);
                    showToast(getString(resID));
                } else if (resID == R.string.liveness_detection_failed) {
                    doPlay(R.raw.meglive_failed);
                } else {
                    doPlay(R.raw.meglive_failed);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void doPlay(int rawId) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.reset();
        try {
            AssetFileDescriptor localAssetFileDescriptor = getResources()
                    .openRawResourceFd(rawId);
            mMediaPlayer.setDataSource(
                    localAssetFileDescriptor.getFileDescriptor(),
                    localAssetFileDescriptor.getStartOffset(),
                    localAssetFileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
        }
    }

    private void showResult(Intent data) {
        Bundle bundle = data.getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get(FACE_RECOGNITION_RETURN_IMG_MAP);
        if (serializableHashMap == null || serializableHashMap.getMap() == null) {
            return;
        }

        Log.e("====msg_face", " ----- showResult ---- " + serializableHashMap.toString());

//		通过映射map.Entry的关系使Map集合变为Set集合
        Set<Map.Entry<String, byte[]>> set = serializableHashMap.getMap().entrySet();

//		以Set集合中实例化一个Iterator类型的集合
        Iterator<Map.Entry<String, byte[]>> iter = set.iterator();

//		迭代输出
        while (iter.hasNext()) {
            Map.Entry<String, byte[]> me = iter.next();
//			直接输出key和value
            Log.e("====msg_face", " ----- showResult ---- " + me.toString());
        }

        Set<Map.Entry<String, String>> stringSet = serializableHashMap.getStringMap().entrySet();
        Iterator<Map.Entry<String, String>> stringIter = stringSet.iterator();
//		迭代输出
        while (iter.hasNext()) {
            Map.Entry<String, String> stringMe = stringIter.next();
//			直接输出key和value
            Log.e("====msg_face", " ----- showResult ---- " + stringMe.toString());
        }

        byte[] imageBestData1 = serializableHashMap.getMap().get(FACE_RECOGNITION_BEST_IMG);
        byte[] imageBestData2 = serializableHashMap.getMap().get(FACE_RECOGNITION_ENV);
        byte[] imageBestData3 = serializableHashMap.getMap().get(FACE_RECOGNITION_ACTION1);
        byte[] imageBestData4 = serializableHashMap.getMap().get(FACE_RECOGNITION_ACTION2);
        byte[] imageBestData5 = serializableHashMap.getMap().get(FACE_RECOGNITION_ACTION3);
        String delta = serializableHashMap.getStringMap().get(FACE_RECOGNITION_KEY);

        LogUtils.d("-----imageBestData1  " + imageBestData1);
        LogUtils.d("-----imageBestData2  " + imageBestData2);
        LogUtils.d("-----imageBestData3  " + imageBestData3);
        LogUtils.d("-----imageBestData4  " + imageBestData4);
        LogUtils.d("-----imageBestData5  " + imageBestData5);

        presenter.doFaceVerify(orderSn, delta, Base64.encodeBase64String(imageBestData1));
        LogUtils.d("-----------  doFaceVerify(): " + delta);
        presenter.doLoadFaceImage(
                orderSn,
                Base64.encodeBase64String(imageBestData2),
                Base64.encodeBase64String(imageBestData3),
                Base64.encodeBase64String(imageBestData4),
                Base64.encodeBase64String(imageBestData5));

    }


    @OnClick({R.id.ll_cert_front, R.id.ll_cert_back, R.id.ll_cert_face, R.id.tv_cert_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cert_front: // 正面照认证
                requestCameraPerm(0);
                break;
            case R.id.ll_cert_back: // 反面照 认证
                if (isFrontOcrVerify) {
                    requestCameraPerm(1);
                } else {
                    showToast("请先完成身份证人像面认证");
                }
                break;
            case R.id.ll_cert_face: // 人脸识别认证

                if (!isFrontOcrVerify) {
                    showToast("请先完成身份证人像面认证");
                } else if (!isBackOcrVerify) {
                    showToast("请先完成身份证国徽面认证");
                } else {
                    // 如果是已认证， 直接关闭页面
                    if (isFaceVerify) {
                        doFaceAfter();
                    } else {
                        requestCameraPerm(-1);
                    }
                }
                break;
            case R.id.tv_cert_btn: // 点击下一步
                LoanDetailRequest request = new LoanDetailRequest(instProductId, orderSn);
                CertRouterUtils.getUserCertifyTypeInfo(mActivity, request);
                break;
        }
    }


    private void doFaceAfter() {
        showToast("认证成功");
    }

    @Override
    public void showOcrStatus(OcrCertStutasResponse response) {
        hideLoading();
        if (response.fontStatus == 1) {
            isFrontOcrVerify = true;
            ivCertFrontIcon.setImageResource(R.drawable.icon_idcard_front_cert);
        }
        if (response.backStatus == 1) {
            isBackOcrVerify = true;
            ivCertBackIcon.setImageResource(R.drawable.icon_idcard_back_cert);
        }

        if (isFaceVerify && isBackOcrVerify && isFrontOcrVerify) {
            tvCertBtn.setEnabled(true);
        }
    }

    @Override
    public void showFaceStatus(int status) {
        hideLoading();
        if (status == 1) {
            isFaceVerify = true;
            ivCertFaceIcon.setImageResource(R.drawable.icon_idcard_face_cert);
        }

        if (isFaceVerify && isBackOcrVerify && isFrontOcrVerify) {
            tvCertBtn.setEnabled(true);
        }
    }

    @Override
    public void doOcrVerifySucc(int side) {
        hideLoading();
        LogUtils.d("---------- ocr succ: " + side);
        if (side == 1) {
            isFrontOcrVerify = true;
            ivCertFrontIcon.setImageResource(R.drawable.icon_idcard_front_cert);
        }
        if (side == 2) {
            isBackOcrVerify = true;
            ivCertBackIcon.setImageResource(R.drawable.icon_idcard_back_cert);
        }
    }

    @Override
    public void doFaceVerifySucc() {
        hideLoading();
        isFaceVerify = true;
        ivCertFaceIcon.setImageResource(R.drawable.icon_idcard_face_cert);

        if (isFaceVerify && isBackOcrVerify && isFrontOcrVerify) {
            tvCertBtn.setEnabled(true);
        }
    }

    @Override
    protected void onBack() {
        finish();
    }

}
