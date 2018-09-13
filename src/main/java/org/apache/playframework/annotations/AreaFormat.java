package org.apache.playframework.annotations;

import java.lang.annotation.*;

/**
 * @author: fuwei
 * @date: 2018/8/21
 * @time: 17:50
 * @description: 地区格式化注解，在使用地区存储数据时，比如 省 市 区 街道共四级，存储了地区的id，没有存储
 * 名称，需要得到 市 或者 区 等来拼接地址
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AreaFormat {

    /**
     * 地区id属性值
     * @return
     */
    String areaIdAttr() default "areaId";

    /**
     * 地区地址属性值
     * @return
     */
    String addressAttr() default "address";

    /**
     * 对象中的子对象属性
     * @return
     */
    String childAttr() default "";

    /**
     * 添加地区的级别，如果是1，
     * @return
     */
    AddAreaLevel addAreaLevel() default AddAreaLevel.PROVINCE;

    /**
     * 间隔符，默认用空格隔开
     * @return
     */
    String apacer() default " ";

    enum AddAreaLevel {

        /**
         * 省
         */
        PROVINCE,

        /**
         * 市
         */
        CITY,

        /**
         * 区
         */
        AREA

    }


}
