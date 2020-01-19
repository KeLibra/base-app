package cn.pingan.claim.app.model.response;

import cn.kezy.libs.common.base.bean.BaseBean;

/**
 * @author:
 * @create_time 2020/1/19 0019
 * @copyright
 * @description:
 */
public class DeviceInfoResponse extends BaseBean {


    /**
     * DevPower : 60%
     * DevSn : 2019120400001
     * DevTotalStorage : 64MB
     * DevUsedStorage : 14MB
     * DevVer : Ver-1.0.0
     * NetInfo : {"GateWay":"192.168.43.1","IpAddr":"192.168.43.2","NetMask":"255.255.255.0"}
     * VideoInfo : {"BitRate":"VBR","Fps":"15","Resolution":"1280x720"}
     */

    public String DevPower; // 设备电量剩余
    public String DevSn;  // #设备序列号
    public String DevTotalStorage; // #设备总空间
    public String DevUsedStorage; // #设备已用空间
    public String DevVer;  // #设备软件版本
    public NetInfoBean NetInfo; // # 网络信息
    public VideoInfoBean VideoInfo; // #视频信息

    public String getDevPower() {
        return DevPower;
    }

    public void setDevPower(String DevPower) {
        this.DevPower = DevPower;
    }

    public String getDevSn() {
        return DevSn;
    }

    public void setDevSn(String DevSn) {
        this.DevSn = DevSn;
    }

    public String getDevTotalStorage() {
        return DevTotalStorage;
    }

    public void setDevTotalStorage(String DevTotalStorage) {
        this.DevTotalStorage = DevTotalStorage;
    }

    public String getDevUsedStorage() {
        return DevUsedStorage;
    }

    public void setDevUsedStorage(String DevUsedStorage) {
        this.DevUsedStorage = DevUsedStorage;
    }

    public String getDevVer() {
        return DevVer;
    }

    public void setDevVer(String DevVer) {
        this.DevVer = DevVer;
    }

    public NetInfoBean getNetInfo() {
        return NetInfo;
    }

    public void setNetInfo(NetInfoBean NetInfo) {
        this.NetInfo = NetInfo;
    }

    public VideoInfoBean getVideoInfo() {
        return VideoInfo;
    }

    public void setVideoInfo(VideoInfoBean VideoInfo) {
        this.VideoInfo = VideoInfo;
    }

    public static class NetInfoBean {
        /**
         * GateWay : 192.168.43.1
         * IpAddr : 192.168.43.2
         * NetMask : 255.255.255.0
         */

        public String GateWay; // #网关
        public String IpAddr;  // #ip地址
        public String NetMask; // #掩码

        public String getGateWay() {
            return GateWay;
        }

        public void setGateWay(String GateWay) {
            this.GateWay = GateWay;
        }

        public String getIpAddr() {
            return IpAddr;
        }

        public void setIpAddr(String IpAddr) {
            this.IpAddr = IpAddr;
        }

        public String getNetMask() {
            return NetMask;
        }

        public void setNetMask(String NetMask) {
            this.NetMask = NetMask;
        }
    }

    public static class VideoInfoBean {
        /**
         * BitRate : VBR
         * Fps : 15
         * Resolution : 1280x720
         */

        public String BitRate; // #码率
        public String Fps; // #帧率
        public String Resolution; // #分辨率

        public String getBitRate() {
            return BitRate;
        }

        public void setBitRate(String BitRate) {
            this.BitRate = BitRate;
        }

        public String getFps() {
            return Fps;
        }

        public void setFps(String Fps) {
            this.Fps = Fps;
        }

        public String getResolution() {
            return Resolution;
        }

        public void setResolution(String Resolution) {
            this.Resolution = Resolution;
        }
    }
}
