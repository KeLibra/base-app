package cn.pingan.claim.app.model.response;

import java.util.List;

/**
 * @author: Kezy
 * @create_time 2020/1/19 0019  18:16
 * @copyright kezy
 * @description:
 */
public class CameraProjectInfoResponse extends CameraBaseResponse {


    public List<FilesBean> Files; // #文件列表

    public List<FilesBean> getFiles() {
        return Files;
    }

    public void setFiles(List<FilesBean> Files) {
        this.Files = Files;
    }

    public static class FilesBean {
        /**
         * type : :"jpeg",#文件类型 图像,    :"video",# 文件类型  视频
         * url : http://192.168.43.2/PA11111/2019110123140554.jpeg
         */

        public String type; // #文件类型 图像
        public String url;  // #文件路径

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
