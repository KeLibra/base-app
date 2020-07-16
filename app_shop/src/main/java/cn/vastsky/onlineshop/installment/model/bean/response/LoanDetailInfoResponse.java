package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/21
 * @description:
 */
public class LoanDetailInfoResponse extends BaseBean {


    /**
     * amountStr : 0.01
     * applyCount : 45345
     * daysPerStages : 7
     * instProductId : 1
     * LXRateStr : 0.69%/月
     * DKTimeLimit : 30
     * DKTimeLimitUnitStr : 天
     * logoPicUrl : http://vs-mall.oss-cn-shanghai.aliyuncs.com/mall-admin/images/20191118/012.jpg
     * moneyExplanation : 月息费率0.96%
     * name : k-分期产品1
     * YQPolicy : 借款日系1%
     * pHKment : 支持随时提前还款
     * HKAmountPerStageStr : 0.00
     * HKAmountStr : 0.01
     * HKmentMode : 银行卡到期自动代扣
     * HKmentPlans : [{"amount":"0.00","LX":"0.00","periodNo":1,"principal":"0.00"},{"amount":"0.00","LX":"0.00","periodNo":2,"principal":"0.00"},{"amount":"0.00","LX":"0.00","periodNo":3,"principal":"0.00"},{"amount":"0.00","LX":"0.00","periodNo":4,"principal":"0.00"}]
     * stages : 4
     * status : 1
     * totalAmount : 0.01
     * userCertifyInfos : [{"icon":"https://vs-mall.oss-cn-shanghai.aliyuncs.com/mall/static/icon_pay_wechat%402x.png","itemCode":"BASE_INFO","name":"基本信息","priority":1,"status":1,"webCertify":true},{"icon":"https://vs-mall.oss-cn-shanghai.aliyuncs.com/mall/static/icon_pay_wechat%402x%20%281%29.png","itemCode":"ID_CARD","name":"身份证","priority":2,"status":0,"webCertify":false},{"icon":"https://vs-mall.oss-cn-shanghai.aliyuncs.com/mall/static/icon_pay_wechat%402x%20%282%29.png","itemCode":"THIRD_PART_INFO","name":"补充信息","priority":3,"status":0,"webCertify":true}]
     *
     * detailUrl: url // 订单详情页 url
     */

    public String amountStr;  //分期金额
    public String applyCount; //申请人数
    public int daysPerStages; //试算每期天数
    public int instProductId;  // 分期产品id
    public String LXRateStr; //息费率
    public String DKTimeLimit; //贷款期限
    public String DKTimeLimitUnitStr; //贷款期限单位
    public String logoPicUrl; //图片地址
    public String moneyExplanation; //费用说明
    public String name;  //产品名称
    public String YQPolicy; //逾期政策
    public String pHKment; //提前还款
    public String HKAmountPerStageStr; //每期还款额
    public String HKAmountStr;  //还款总额
    public String HKmentMode; //还款方式
    public int stages; //期数
    public int status;  //状态 1-确认申请 2-审批中 3-审批被拒
    public String totalAmount; //试算总计需还
    public List<RepaymentPlansBean> HKmentPlans; //试算还款计划
    public List<UserCertifyInfosBean> userCertifyInfos;  //认证项
    public String detailUrl; // 订单详情页



    public static class RepaymentPlansBean {
        /**
         * amount : 0.00
         * LX : 0.00
         * periodNo : 1
         * principal : 0.00
         */

        public String amount;  //需还
        public String LX; //息费
        public int periodNo;  //期数
        public String principal; //本金


    }

    public static class UserCertifyInfosBean {
        /**
         * icon : https://vs-mall.oss-cn-shanghai.aliyuncs.com/mall/static/icon_pay_wechat%402x.png
         * itemCode : BASE_INFO
         * name : 基本信息
         * priority : 1
         * status : 1
         * webCertify : true
         */

        public String icon; //图片
        public String name; //基本信息
        public int status;  //状态  0-未认证 1-已认证 2-认证中

    }
}
