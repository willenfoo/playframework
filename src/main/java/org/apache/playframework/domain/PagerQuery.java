package org.apache.playframework.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PagerQuery implements Serializable {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页，默认 1", example = "1")
    private long current = 1;

    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数，默认 10", example = "10")
    private long size = 10;

    public PagerQuery() {

    }

    public PagerQuery(long current, long size) {
        this.current = current;
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }




    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "PagerQuery{" +
                "size=" + size +
                ", current=" + current +
                '}';
    }
}
