package org.apache.playframework.annotations;

import io.swagger.annotations.ApiImplicitParam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: fuwei
 * @date: 2018/8/22
 * @time: 20:13
 * @description: 地区格式化注解，在使用地区存储数据时，比如 省 市 区 街道共四级，存储了地区的id，没有存储
 *  * 名称，需要得到 市 或者 区 等来拼接地址
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AreaFormats {

    /**
     * A list of {@link ApiImplicitParam}s available to the API operation.
     */
    AreaFormat[] value();

}
