package cn.vastsky.onlineshop.installment.contract;


import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;
import cn.vastsky.onlineshop.installment.model.bean.response.OcrCertStutasResponse;

/**
 * Created by airal on 2019/1/12.
 */

public interface ILoanIdCardCertContract {
    interface ILoanIdCardCertContractView extends IBaseView {
        void showOcrStatus(OcrCertStutasResponse response);

        void showFaceStatus(int status);

        void doOcrVerifySucc(int side); // Ocr 认证成功

        void doFaceVerifySucc(); // 人脸认证成功

    }

    interface ILoanIdCardCertContractViewPresenter extends IBasePresenter<ILoanIdCardCertContractView> {
        void getOcrStatus();

        void getFaceStatus(String orderSn);

        void doOcrVerify(int side, String imgBase64);

        void doFaceVerify(String orderSn, String delta, String imgBase64);

        void doLoadFaceImage(String orderSn, String img1, String img2, String img3, String img4);
    }
}
