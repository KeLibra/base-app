package cn.pingan.claim.app.model.request;

import cn.kezy.libs.common.base.bean.BaseBean;

/**
 * @author: Kezy
 * @create_time 2020/1/26 0026  14:50
 * @copyright kezy
 * @description:
 */
public class CameraBaseRequest extends BaseBean {

    public String DevSn; // 设备序列号
    public String PrjNo; // 新建的项目编号


    public CameraBaseRequest(String devSn) {
        DevSn = devSn;
    }

    public CameraBaseRequest(String devSn, String prjNo) {
        DevSn = devSn;
        PrjNo = prjNo;
    }
}
