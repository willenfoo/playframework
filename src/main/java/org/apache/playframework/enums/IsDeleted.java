package org.apache.playframework.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: fuwei
 * @date: 2018/8/15
 * @time: 9:52
 * @description: 是否删除的公共枚举
 */
@JSONType(serializeEnumAsJavaBean = true)
public enum IsDeleted implements IEnum {

    /**
     * 是
     */
    YES(1, "是"),

    /**
     * 否
     */
    NO(0, "否");

    private int value;

    private String desc;

    IsDeleted(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }

}
