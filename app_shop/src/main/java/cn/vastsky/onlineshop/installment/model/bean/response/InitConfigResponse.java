package cn.vastsky.onlineshop.installment.model.bean.response;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/20
 * @description:
 */
public class InitConfigResponse extends BaseBean {


    /**
     * antiCheckFlag : true
     * bottomTab : {"orderUrl":"url"}
     * docUrl : {"registerDocUrl":"http://xxx/fdsfds/fjsldf.html"}
     */

    public boolean ckFlag;
    public BottomTabBean bottomTab;
    public DocUrlBean docUrl;


    public BottomTabBean getBottomTab() {
        return bottomTab;
    }

    public void setBottomTab(BottomTabBean bottomTab) {
        this.bottomTab = bottomTab;
    }

    public DocUrlBean getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(DocUrlBean docUrl) {
        this.docUrl = docUrl;
    }

    public static class BottomTabBean {
        /**
         * orderUrl : url
         */

        public String orderUrl;

        public String getOrderUrl() {
            return orderUrl;
        }

        public void setOrderUrl(String orderUrl) {
            this.orderUrl = orderUrl;
        }
    }

    public static class DocUrlBean {
        /**
         * registerDocUrl : http://xxx/fdsfds/fjsldf.html
         * privacyPolicyDocUrl : http://xxx/fdsfds/fjsldf.html
         */

        public String registerDocUrl;
        public String privacyPolicyDocUrl;

        public String getRegisterDocUrl() {
            return registerDocUrl;
        }

        public void setRegisterDocUrl(String registerDocUrl) {
            this.registerDocUrl = registerDocUrl;
        }
    }
}
