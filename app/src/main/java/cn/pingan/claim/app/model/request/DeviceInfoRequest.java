package cn.pingan.claim.app.model.request;

import cn.kezy.libs.common.base.bean.BaseBean;

/**
 * @author:
 * @create_time 2020/1/19 0019
 * @copyright
 * @description:
 */
public class DeviceInfoRequest extends BaseBean {

    public String AppIp; // "192.168.43.1”,# App自身的热点Ip地址
    public int UdpPort; // 10004 #Udp通信的端口

    public DeviceInfoRequest(String appIp, int udpPort) {
        AppIp = appIp;
        UdpPort = udpPort;
    }
}
