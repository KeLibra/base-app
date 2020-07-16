package cn.vastsky.onlineshop.installment.presenter;


import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.base.BasePresenter;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.contract.ILoanIdCardCertContract;
import cn.vastsky.onlineshop.installment.model.bean.request.FaceImgUploadRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.FaceVerifyRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.OcrVerifyRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.FaceCertStatusResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.OcrCertStutasResponse;
import cn.vastsky.onlineshop.installment.service.LoanModel;

/**
 * Created by airal on 2019/1/12.
 */

public class LoanIdCardCertPresenter
        extends BasePresenter<ILoanIdCardCertContract.ILoanIdCardCertContractView>
        implements ILoanIdCardCertContract.ILoanIdCardCertContractViewPresenter {

    public LoanIdCardCertPresenter(ILoanIdCardCertContract.ILoanIdCardCertContractView view) {
        super(view);
    }

    @Override
    public void getOcrStatus() {
        getView().showLoading("");
        LoanModel.getOcrCertInfo(new ICallBack<OcrCertStutasResponse>() {
            @Override
            public void onSuccess(OcrCertStutasResponse response) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    if (response != null) {
                        getView().showOcrStatus(response);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void getFaceStatus(String orderSn) {
        LoanModel.getFaceCertInfo(orderSn, new ICallBack<FaceCertStatusResponse>() {
            @Override
            public void onSuccess(FaceCertStatusResponse faceCertStatusResponse) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    if (faceCertStatusResponse != null) {
                        getView().showFaceStatus(faceCertStatusResponse.status);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void doOcrVerify(int side, String imgBase64) {
        getView().showLoading("");
        OcrVerifyRequest request = new OcrVerifyRequest(imgBase64, side);
        LoanModel.doOcrVerify(request, new ICallBack() {
            @Override
            public void onSuccess(Object o) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().doOcrVerifySucc(side);
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void doFaceVerify(String orderSn, String delta, String imgBase64) {
        getView().showLoading("");
        LoanModel.doFaceVerify(new FaceVerifyRequest(orderSn, delta, imgBase64), new ICallBack() {
            @Override
            public void onSuccess(Object o) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().doFaceVerifySucc();
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void doLoadFaceImage(String orderSn, String img1, String img2, String img3, String img4) {
        LoanModel.doFaceImgUpload(new FaceImgUploadRequest(orderSn, img1, img2, img3, img4), new ICallBack<Object>() {
            @Override
            public void onSuccess(Object apiOcrVerifyResponse) {
                getView().hideLoading();
            }

            @Override
            public void onFailure(String err_msg) {
                hideLoading();
                if (getView() != null) {
                    getView().showToast(err_msg);
                }
            }
        });
    }
}
