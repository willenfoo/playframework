package org.apache.playframework.rocketmq;

/**
 * 分布式事务参数对象
 * @author fuwei
 */
public class TransactionParam {

    /**
     * 入参
     */
    private Object inputParam;

    /**
     * 出参
     */
    private Object outParam;

    /**
     * 异常
     */
    private Exception exception;

    public TransactionParam(Object inputParam) {
        this.inputParam = inputParam;
        this.outParam = outParam;
        this.exception = exception;
    }

    public Object getInputParam() {
        return inputParam;
    }

    public void setInputParam(Object inputParam) {
        this.inputParam = inputParam;
    }

    public Object getOutParam() {
        return outParam;
    }

    public void setOutParam(Object outParam) {
        this.outParam = outParam;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
