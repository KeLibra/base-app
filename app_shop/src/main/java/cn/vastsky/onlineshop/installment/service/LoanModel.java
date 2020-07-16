package cn.vastsky.onlineshop.installment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.vastsky.onlineshop.installment.common.net.BaseCallBack;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.net.ServiceFactory;
import cn.vastsky.onlineshop.installment.model.bean.request.ContractRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.FaceImgUploadRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.FaceVerifyRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanCertRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.OcrVerifyRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.CertifyTpyeResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.ContractResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.FaceCertStatusResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.OcrCertStutasResponse;


/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public class LoanModel {

    public static LoanService loanService = ServiceFactory.createService(LoanService.class);

    /**
     * 首页信息接口
     *
     * @param iCallBack
     */
    public static void getLoanDetailInfo(LoanDetailRequest request, final ICallBack<LoanDetailInfoResponse> iCallBack) {
        loanService.getProductDetailInfo(request)
                .enqueue(new BaseCallBack<LoanDetailInfoResponse>() {
                    @Override
                    public void onSuccess(@Nullable LoanDetailInfoResponse infoResponse) {
                        iCallBack.onSuccess(infoResponse);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    /**
     * 协议信息
     *
     * @param request
     * @param iCallBack
     */
    public static void getContractInfo(ContractRequest request, ICallBack<ContractResponse> iCallBack) {
        loanService.getContractInfo(request)
                .enqueue(new BaseCallBack<ContractResponse>() {
                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }

                    @Override
                    public void onSuccess(@Nullable ContractResponse response) {
                        iCallBack.onSuccess(response);
                    }
                });
    }

    /**
     * 获取认证项 下一步
     *
     * @param request
     * @param iCallBack
     */
    public static void getUserCertifyInfo(LoanDetailRequest request, ICallBack<CertifyTpyeResponse> iCallBack) {
        loanService.getUserCertifyInfo(request)
                .enqueue(new BaseCallBack<CertifyTpyeResponse>() {
                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }

                    @Override
                    public void onSuccess(@Nullable CertifyTpyeResponse response) {
                        iCallBack.onSuccess(response);
                    }
                });
    }

    /**
     * 获取认证项 信息
     *
     * @param request
     * @param iCallBack
     */
    public static void getCertDestinationInfo(LoanCertRequest request, ICallBack<String> iCallBack) {
        loanService.getCertDestinationInfo(request)
                .enqueue(new BaseCallBack<String>() {
                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }

                    @Override
                    public void onSuccess(@Nullable String response) {
                        iCallBack.onSuccess(response);
                    }
                });
    }



    public static void getOcrCertInfo(ICallBack<OcrCertStutasResponse> iCallBack) {
        loanService.getOcrStatus(new Object()).enqueue(new BaseCallBack<OcrCertStutasResponse>() {
            @Override
            public void onSuccess(@Nullable OcrCertStutasResponse ocrInfoResponse) {
                iCallBack.onSuccess(ocrInfoResponse);
            }

            @Override
            public void onFail(@NotNull String code, @NotNull String errMsg) {
                iCallBack.onFailure(errMsg);
            }
        });
    }

    public static void getFaceCertInfo(String orderSn, ICallBack<FaceCertStatusResponse> iCallBack) {
        loanService.getFaceStatus(new LoanDetailRequest(orderSn)).enqueue(new BaseCallBack<FaceCertStatusResponse>() {
            @Override
            public void onSuccess(@Nullable FaceCertStatusResponse ocrInfoResponse) {
                iCallBack.onSuccess(ocrInfoResponse);
            }

            @Override
            public void onFail(@NotNull String code, @NotNull String errMsg) {
                iCallBack.onFailure(errMsg);
            }
        });
    }

    public static void doOcrVerify(OcrVerifyRequest request, ICallBack iCallBack) {
        loanService.doOcrVerify(request).enqueue(new BaseCallBack() {
            @Override
            public void onSuccess(@Nullable Object ocrInfoResponse) {
                iCallBack.onSuccess(ocrInfoResponse);
            }

            @Override
            public void onFail(@NotNull String code, @NotNull String errMsg) {
                iCallBack.onFailure(errMsg);
            }
        });
    }

    public static void doFaceVerify(FaceVerifyRequest request, ICallBack iCallBack) {
        loanService.doFaceVerify(request).enqueue(new BaseCallBack() {
            @Override
            public void onSuccess(@Nullable Object ocrInfoResponse) {
                iCallBack.onSuccess(ocrInfoResponse);
            }

            @Override
            public void onFail(@NotNull String code, @NotNull String errMsg) {
                iCallBack.onFailure(errMsg);
            }
        });
    }

    public static void doFaceImgUpload(FaceImgUploadRequest request, ICallBack iCallBack) {
        loanService.doFaceImgUpload(request).enqueue(new BaseCallBack() {
            @Override
            public void onSuccess(@Nullable Object ocrInfoResponse) {
                iCallBack.onSuccess(ocrInfoResponse);
            }

            @Override
            public void onFail(@NotNull String code, @NotNull String errMsg) {
                iCallBack.onFailure(errMsg);
            }
        });
    }
}
