package cn.vastsky.onlineshop.installment.model;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * Created by airal on 2018/10/16.
 */

public class BaseElement extends BaseBean {
     public String name;// "发现", //元素名称
     public String description;// "发现tab", //描述
     public String eventName;// "100019920222", //埋点名称
     public int style;// 1, //元素类型{1}>图标;{2}>列表;{3}>底部bar;{4}>文字样式;{5}>控件样式
     public String selectImageUrl;// "", //选中图片
     public String unSelectImageUrl;// "", //未选中图片
     public int jumpType;// 2, //跳转类型 1:app协议 2:h5 3:不跳转
     public String url;// "http://www.baidu.com",//链接

     @Override
     public String toString() {
          return "BaseElement{" +
                  "name='" + name + '\'' +
                  ", description='" + description + '\'' +
                  ", eventName='" + eventName + '\'' +
                  ", style=" + style +
                  ", selectImageUrl='" + selectImageUrl + '\'' +
                  ", unSelectImageUrl='" + unSelectImageUrl + '\'' +
                  ", jumpType=" + jumpType +
                  ", url='" + url + '\'' +
                  '}';
     }
}
