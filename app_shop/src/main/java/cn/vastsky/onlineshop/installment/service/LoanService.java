package cn.vastsky.onlineshop.installment.service;

import cn.vastsky.onlineshop.installment.model.bean.base.BaseResponse;
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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface LoanService {


    // 信贷产品详情
    @POST("/inst/product/v1/productDetail")
    Call<BaseResponse<LoanDetailInfoResponse>> getProductDetailInfo(@Body LoanDetailRequest request);

    // 合同接口
    @POST("/inst/contract/get")
    Call<BaseResponse<ContractResponse>> getContractInfo(@Body ContractRequest request);


    /**
     * 获取当前认证项及订单创建
     *
     * @param request 用两个  参数 的request
     * @return
     */
    @POST("/inst/user/certifyInfos")
    Call<BaseResponse<CertifyTpyeResponse>> getUserCertifyInfo(@Body LoanDetailRequest request);

    /**
     * 获取 h5 认证项跳转地址
     *
     * @return
     */
    @POST("/inst/redirect/get-cert-destination")
    Call<BaseResponse<String>> getCertDestinationInfo(@Body LoanCertRequest request);


/** 认证项 相关接口**/

    /**
     * 身份证OCR认证状态查询
     */
    @POST("/inst/user/ocr/get")
    Call<BaseResponse<OcrCertStutasResponse>> getOcrStatus(@Body Object request);


    /**
     * 活体认证状态查询
     */
    @POST("/inst/user/face/get")
    Call<BaseResponse<FaceCertStatusResponse>> getFaceStatus(@Body LoanDetailRequest request);

    /**
     * 身份证 认证接口
     */
    @POST("/inst/user/ocr/verify")
    Call<BaseResponse<Object>> doOcrVerify(@Body OcrVerifyRequest request);

    /**
     * face 认证接口
     */
    @POST("/inst/user/face/verify")
    Call<BaseResponse<Object>> doFaceVerify(@Body FaceVerifyRequest request);

    /**
     * 人脸活体照片上传接口
     */
    @POST("/inst/user/face/uploadImages")
    Call<BaseResponse<Object>> doFaceImgUpload(@Body FaceImgUploadRequest request);

}
