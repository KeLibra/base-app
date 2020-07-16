package cn.vastsky.onlineshop.installment.model.bean.request;

/**
 * Created by airal on 2019/1/11.
 */

public class FaceVerifyRequest {

    public String orderSn;

    public String delta;//"",//delta值
    public String faceImg;//""//人脸照片

    public FaceVerifyRequest(String orderSn, String delta, String faceImg) {
        this.orderSn = orderSn;
        this.delta = delta;
        this.faceImg = faceImg;
    }
}
