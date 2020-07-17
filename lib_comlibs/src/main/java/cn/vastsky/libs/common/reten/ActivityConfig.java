package cn.vastsky.libs.common.reten;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: kezy
 * @create_time: 2020/07/16  14:56
 * @description: activity 一些activity配置相关的 注解， 后期activity 相关配置都可以统一管理
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityConfig {
    boolean isRegisterEventBus() default false;  // 是否需要注册eventbus

    boolean isHasErrorPageView() default false; // 是否 需要默认err页面

    boolean isHasTitleView() default true; // 是否有标题栏

    String titleDesc() default ""; // 标题栏内容
}
