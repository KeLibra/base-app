package cn.vastsky.onlineshop.installment.model.bean.response;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/29
 * @description:
 */
public class AppUpdateResponse extends BaseBean {

    public int optional; //   1强制 2可选 3不更新
    public String targetVersion; //  目标版本
    public String info; // 更新内容
    public String downloadUrl; // 下载链接

}
