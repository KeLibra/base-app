package cn.vastsky.libs.gdlocation.config;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class LocationConstant {

    public static final String DEFAULT_ADDR_INFO = "未知";
    public static final String DEFAULT_CODE_INFO = "0";

    public interface RangeType {
        int INLAND = 0;//国内
        int OUTLAND = 1;//国外
    }

    public static final String DEFAULT_CITY_CODE = DEFAULT_CODE_INFO;
    public static final String DEFAULT_PROVINCE_CODE = DEFAULT_CODE_INFO;
    public static final String DEFAULT_DISTRICT_CODE = DEFAULT_CODE_INFO;

    public static final String DEFAULT_CITY_NAME = DEFAULT_ADDR_INFO;
    public static final String DEFAULT_CITY_PROVINCE = DEFAULT_ADDR_INFO;
    public static final String DEFAULT_CITY_DISTRICT = DEFAULT_ADDR_INFO;
    public static final String DEFAULT_CITY_STREET = DEFAULT_ADDR_INFO;
}
