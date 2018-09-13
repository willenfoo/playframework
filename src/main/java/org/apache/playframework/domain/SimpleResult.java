package org.apache.playframework.domain;

/**
 * @author: fuwei
 * @date: 2018/7/27
 * @time: 16:10
 * @description: 简单返回结果类，api结果只需要返回一个字段的这种结果，如果以后需要返回多个字段，自己新建对象
 */
public class SimpleResult<T> {
    /**
     * 简单结果
     */
    private T result;

    public SimpleResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
