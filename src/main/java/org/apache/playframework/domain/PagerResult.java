package org.apache.playframework.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

public class PagerResult<T> {

    /**
     * 查询数据列表
     */
    @ApiModelProperty(value = "查询数据列表")
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private long total = 0;

    public PagerResult() {

    }

    public PagerResult(List<T> records, long total) {
        this.records = records;
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
