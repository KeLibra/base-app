package cn.pingan.claim.app.model.response;

import java.util.List;

/**
 * @author: Kezy
 * @create_time 2020/1/19 0019  18:11
 * @copyright kezy
 * @description:
 */
public class CameraProjectListResponse extends CameraBaseResponse{


    public List<String> Prjs;  // #项目（案件）列表

    public List<String> getPrjs() {
        return Prjs;
    }

    public void setPrjs(List<String> Prjs) {
        this.Prjs = Prjs;
    }
}
