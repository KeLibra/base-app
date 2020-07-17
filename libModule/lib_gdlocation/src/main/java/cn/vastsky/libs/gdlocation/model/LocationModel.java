package cn.vastsky.libs.gdlocation.model;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 *
 * 定位信息 参数
 */
public class LocationModel {
    public double latitude; // 纬度
    public double longitude; // 经度
    public String address; //地址信息
    public String country; // 国家
    public String province; // 省份
    public String city; // 城市信息
    public String district; // 区信息
    public String street; // 街区信息
    public String road;
    public String poi;
    public String provinceCode;
    public String cityCode;
    public String districtCode;
    public String countryCode;

    @Override
    public String toString() {
        return "LocationModel{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", road='" + road + '\'' +
                ", poi='" + poi + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", districtCode='" + districtCode + '\'' +
                '}';
    }
}
