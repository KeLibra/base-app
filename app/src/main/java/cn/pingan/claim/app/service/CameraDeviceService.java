package cn.pingan.claim.app.service;

import cn.pingan.claim.app.model.request.DeviceInfoRequest;
import cn.pingan.claim.app.model.response.CameraBaseResponse;
import cn.pingan.claim.app.model.response.CameraProjectInfoResponse;
import cn.pingan.claim.app.model.response.CameraProjectListResponse;
import cn.pingan.claim.app.model.response.DeviceInfoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
    Call<DeviceInfoResponse> getDeviceInfo(@Body DeviceInfoRequest request);

    /**
     * 2 创建新项目通知
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#新建的项目编号
     */
    @POST("/app/newprj")
    Call<CameraBaseResponse> newProject(@Field("DevSn") String DevSn, @Field("PrjNo") String PrjNo);


    /**
     * 3 获取设备保存的项目列表
     * <p>
     * DevSn： 设备序列号
     */
    @POST("/app/prjs")
    Call<CameraProjectListResponse> getProjectList(@Field("DevSn") String DevSn);

    /**
     * 4 获取设备保存的某个项目详细信息
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/prjinfo")
    Call<CameraProjectInfoResponse> getProjectInfo(@Field("DevSn") String DevSn, @Field("PrjNo") String PrjNo);

    /**
     * 5. 获取当前录像情况
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/recordstate")
    Call<CameraBaseResponse> getCameraStatus(@Body Object o);


    /**
     * 6. 请求进行录像记录
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/record")
    Call<CameraBaseResponse> recordCamera(@Field("DevSn") String DevSn, @Field("PrjNo") String PrjNo);

    /**
     * 7. 请求进行抓拍记录
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/snap")
    Call<CameraBaseResponse> snapCamera(@Field("DevSn") String DevSn, @Field("PrjNo") String PrjNo);

    /**
     * 8. 开启直播
     * <p>
     * DevSn： 设备序列号
     * PrjNo：#项目编号
     */
    @POST("/app/live")
    Call<CameraBaseResponse> liveCamera(@Field("DevSn") String DevSn, @Field("PrjNo") String PrjNo);


}
