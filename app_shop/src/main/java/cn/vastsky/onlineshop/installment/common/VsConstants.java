package cn.vastsky.onlineshop.installment.common;

/**
 * @author: kezy
 * @create_time 2019/11/18
 * @description:
 */
public class VsConstants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxfc56b40e4049b17e";


    public interface Key {
        String KEY_ORDER_LIST = "order_list_key";
        String KEY_REGISTER_DOC_URL = "register_doc_url";
        String KEY_PRIVACY_DOC_URL = "privacy_policy_docUrl";
    }


    public static interface NetWork {
        int PRD = 1; // 生产环境
        int SIT = 2; // sit 环境
    }

    public interface RouterCommon {
        String SCHEME_HTTPS = "https";
        String SCHEME_NAGELAN = "nagelan"; // 纳格兰 scheme

        String PATH_PAY_MENT = "/page/cashier/home";  //  收银台
        String PATH_LOAN_DETAIL = "/page/auth/detail";  // 信贷产品详情
        String PATH_WEB = "/page/web"; // 跳到 webview


        String PAGE_LOCAL_BASE = "://hybrid";
        String PATH_CLOSE_WEB = "/page/android/close/web"; // 关闭webview
        String PATH_THIRD_CERT_CALLBACK = "/page/android/thirdcert/callback"; // 关闭webview
    }

    public interface DelegatePage {
        // 页面地址
        String CLASS_PAGE_HOME = "cn.vastsky.onlineshop.installment.view.activity.HomepageActivity";
        String CLASS_PAGE_ORDER = "cn.vastsky.onlineshop.installment.base.web.VsWebviewActivity";
        String CLASS_PAGE_MINE = "cn.vastsky.onlineshop.installment.view.activity.MinePageActivity";
        String CLASS_PAGE_WEB = "";
    }
}
