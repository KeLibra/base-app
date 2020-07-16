package cn.vastsky.onlineshop.installment.model.bean.request;

/**
 * Created by airal on 2019/1/11.
 */

public class FaceImgUploadRequest {

    public String orderSn; //商城订单号

    public String image1;//"",//全景图
    public String image2;//"",//动作1
    public String image3;//"",//动作2
    public String image4;//""//动作3

    public FaceImgUploadRequest(String orderSn, String image1, String image2, String image3, String image4) {
        this.orderSn = orderSn;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }
}
