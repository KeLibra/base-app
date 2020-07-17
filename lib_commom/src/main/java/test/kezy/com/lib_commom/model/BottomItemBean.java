package test.kezy.com.lib_commom.model;

import cn.vastsky.lib.base.common.base.bean.BaseBean;


/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class BottomItemBean extends BaseBean {
    public String selectedUrl; // 图片地址
    public String unSelectUrl; // 图片地址
    public int selectedResource; // 图片资源
    public int unSelectResource; // 图片资源
    public String itemName; //

    public BottomItemBean(int selectedResource, int unSelectResource, String itemName) {
        this.selectedResource = selectedResource;
        this.unSelectResource = unSelectResource;
        this.itemName = itemName;
    }

    public BottomItemBean(String selectedUrl, String unSelectUrl, int selectedResource, int unSelectResource, String itemName) {
        this.selectedUrl = selectedUrl;
        this.unSelectUrl = unSelectUrl;
        this.selectedResource = selectedResource;
        this.unSelectResource = unSelectResource;
        this.itemName = itemName;
    }

    public String getSelectedUrl() {

        return selectedUrl;
    }

    public void setSelectedUrl(String selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public String getUnSelectUrl() {
        return unSelectUrl;
    }

    public void setUnSelectUrl(String unSelectUrl) {
        this.unSelectUrl = unSelectUrl;
    }

    public int getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(int selectedResource) {
        this.selectedResource = selectedResource;
    }

    public int getUnSelectResource() {
        return unSelectResource;
    }

    public void setUnSelectResource(int unSelectResource) {
        this.unSelectResource = unSelectResource;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
