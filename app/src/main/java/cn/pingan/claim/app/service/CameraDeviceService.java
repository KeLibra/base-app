package cn.pingan.claim.app.service;

import cn.pingan.claim.app.model.request.CameraBaseRequest;
import cn.pingan.claim.app.model.request.DeviceInfoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author:
 * @create_time 2020/1/19 0019
 * @copyright
 * @description:
 */
public interface CameraDeviceService {

    /**
     * 1 获取设备信息
     */
    @POST("/app/deviceinfo")
    Call<String> getDeviceInfo(@Body DeviceInfoRequest request);

    /**
     * 2 创建新项目通知
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#新建的项目编号
     */
    @POST("/app/newprj")
    Call<String> newProject(@Body CameraBaseRequest request);


    /**
     * 3 获取设备保存的项目列表
     * <p>
     * DevSn： 设备序列号
     */
    @POST("/app/prjs")
    Call<String> getProjectList(@Body CameraBaseRequest request);

    /**
     * 4 获取设备保存的某个项目详细信息
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/prjinfo")
    Call<String> getProjectInfo(@Body CameraBaseRequest request);

    /**
     * 5. 获取当前录像情况
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/recordstate")
    Call<String> getCameraStatus(@Body Object o);


    /**
     * 6. 请求进行录像记录
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/record")
    Call<String> recordCamera(@Body CameraBaseRequest request);

    /**
     * 7. 请求进行抓拍记录
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/snap")
    Call<String> snapCamera(@Body CameraBaseRequest request);

    /**
     * 8. 开启直播
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/live")
    Call<String> liveCamera(@Body CameraBaseRequest request);


}
