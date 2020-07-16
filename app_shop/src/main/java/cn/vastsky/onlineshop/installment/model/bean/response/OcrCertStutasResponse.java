package cn.vastsky.onlineshop.installment.model.bean.response;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 *
 */


public class OcrCertStutasResponse extends BaseBean {

    /**
     * fontStatus==1 && backStatus==1 认证成功 否则认证失败
     */

    public int fontStatus;//1,//身份证正面-认证状态 1.已认证，其他：未认证
    public int backStatus;//1//身份证反面-认证状态  1.已认证，其他：未认证
    public String ocrId;//"321283199408240613",//身份证号码
    public String ocrName;//"丁中胜",//姓名
    public String idCardFront;//"图片地址",//正面图片
    public String idCardBack;//"",//反面图片
}
