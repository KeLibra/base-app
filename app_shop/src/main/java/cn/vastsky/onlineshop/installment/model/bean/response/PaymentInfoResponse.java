package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/15
 * @description:
 */
public class PaymentInfoResponse extends BaseBean {


    /**
     * create_time : 12345678
     * orderId : 21212
     * orderSn :
     * payAmount : 1030.00
     * payChannel : {"alipay":1,"productList":[{"logoPicUrl":"","name":"北极分期","productDesc":"","productId":200,"productStatus":""}],"weixin":1}
     * payType : 3
     * timeout : 86400
     * orderDetailUrl: //跳订单详情页URL
     */

    public long orderId;
    public String orderSn;
    public String payAmount;
    public PayChannelBean payChannel;
    public int payType;
    public long timeout;
    public String orderDetailUrl;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public PayChannelBean getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannelBean payChannel) {
        this.payChannel = payChannel;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public static class PayChannelBean extends BaseBean {
        /**
         * alipay : 1
         * productList : [{"logoPicUrl":"","name":"北极分期","productDesc":"","productId":200,"productStatus":""}]
         * weixin : 1
         * hasProduct : 1 //是否有可申请的分期 1有  0无
         */

        public int alipay;
        public int weixin;
        public int hasProduct;

        public List<LoanItemBean> productList;

        public int getAlipay() {
            return alipay;
        }

        public void setAlipay(int alipay) {
            this.alipay = alipay;
        }

        public int getWeixin() {
            return weixin;
        }

        public void setWeixin(int weixin) {
            this.weixin = weixin;
        }

        public List<LoanItemBean> getProductList() {
            return productList;
        }

        public void setProductList(List<LoanItemBean> productList) {
            this.productList = productList;
        }

        public int getHasProduct() {
            return hasProduct;
        }

        public void setHasProduct(int hasProduct) {
            this.hasProduct = hasProduct;
        }

        public static class LoanItemBean {
            /**
             * logoPicUrl :
             * name : 北极分期
             * productDesc :
             * productId : 200
             * productStatus :
             * condition: // //产品描述
             */

            public String logoPicUrl;
            public String name;
            public String productDesc;
            public int productId;
            public int productStatus;
            public String condition; //产品描述

            public String getLogoPicUrl() {
                return logoPicUrl;
            }

            public void setLogoPicUrl(String logoPicUrl) {
                this.logoPicUrl = logoPicUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProductDesc() {
                return productDesc;
            }

            public void setProductDesc(String productDesc) {
                this.productDesc = productDesc;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(int productStatus) {
                this.productStatus = productStatus;
            }
        }
    }
}
