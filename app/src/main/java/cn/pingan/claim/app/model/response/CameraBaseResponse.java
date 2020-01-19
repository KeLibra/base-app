package cn.pingan.claim.app.model.response;

import cn.kezy.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2020/1/19 0019
 * @copyright kezy
 * @description:
 */
public class CameraBaseResponse extends BaseBean {
    public String ResCode; //  "Success",#结果编码 ,  Failure : 失败

    public String  State; //  Recording"  #状态，正在录制    "Idle"  #状态， 闲置
}
