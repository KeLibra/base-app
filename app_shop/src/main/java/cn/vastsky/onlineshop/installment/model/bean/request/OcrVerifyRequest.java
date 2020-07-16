package cn.vastsky.onlineshop.installment.model.bean.request;

/**
 */

public class OcrVerifyRequest {
    public String imageBase64;//"图片png,base64编码",//最大不能超过2M app端需要处理压缩
    public int side;//1//身份证正反面 1正面人脸 2反面国徽
    public OcrVerifyRequest(String imageBase64, int side){
        this.imageBase64 = imageBase64;
        this.side = side;
    }
}
