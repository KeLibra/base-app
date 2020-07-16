package cn.vastsky.onlineshop.installment.model.bean.response;

/**
 * @author: kezy
 * @create_time 2019/11/22
 * @description:
 */
public class CertifyTpyeResponse {

    /**
     * certifyType : 1    “webCertify” ：false
     */

    /**
     * 1-基本信息(h5)
     * 2-身份证认证
     * 3-补充信息(h5)
     * 4-认证完结
     * 5-申请失败（弹窗）
     * 其他类型且webCertify为true，统一按h5认证处理
     */
    public int certifyType; //
    /**
     * //是否是h5
     * 是h5 的话 调 认证项接口
     */
    public boolean webCertify;

    public String orderSn; // order Sn

    public int instProductId; // 信贷产品id
}
