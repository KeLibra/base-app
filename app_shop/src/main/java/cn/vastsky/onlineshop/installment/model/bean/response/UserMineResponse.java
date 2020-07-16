package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/25
 * @description:
 */
public class UserMineResponse extends BaseBean {

    /**
     * phone : 137****3343
     * recentNeedHKAmount : 0.00
     * totalNeedHKAmount : 0.00
     * zdDetailUrl: //账单详情页URL
     * urlInfo : [{"order":"1","text":"把APP分享给好友","url":""},{"order":"2","text":"帮助中心","url":""},{"order":"3","text":"客户服务","url":""},{"order":"4","text":"关于","url":""}]
     */

    public String phone;
    public String recentNeedHKAmount;
    public String totalNeedHKAmount;
    public String zdDetailUrl;
    public List<UrlInfoBean> urlInfo;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecentNeedHKAmount() {
        return recentNeedHKAmount;
    }

    public void setRecentNeedHKAmount(String recentNeedHKAmount) {
        this.recentNeedHKAmount = recentNeedHKAmount;
    }

    public String getTotalNeedHKAmount() {
        return totalNeedHKAmount;
    }

    public void setTotalNeedHKAmount(String totalNeedHKAmount) {
        this.totalNeedHKAmount = totalNeedHKAmount;
    }

    public List<UrlInfoBean> getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(List<UrlInfoBean> urlInfo) {
        this.urlInfo = urlInfo;
    }

    public static class UrlInfoBean extends BaseBean {
        /**
         * order : 1
         * text : 把APP分享给好友
         * url :
         */

        public String order;
        public String text;
        public String url;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
