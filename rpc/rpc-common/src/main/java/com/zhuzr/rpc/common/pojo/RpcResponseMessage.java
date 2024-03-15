package com.zhuzr.rpc.common.pojo;

public class RpcResponseMessage extends RpcMessage {
    // 请求序号
    int sequenceId;
    // 异常状态，正常响应为null
    Exception exceptionValue;
    // 响应返回的信息
    Object responseValue;

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Exception getExceptionValue() {
        return exceptionValue;
    }

    public void setExceptionValue(Exception exceptionValue) {
        this.exceptionValue = exceptionValue;
    }

    public Object getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(Object responseValue) {
        this.responseValue = responseValue;
    }

    public RpcResponseMessage() {
    }

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }

    public RpcResponseMessage(int sequenceId, Exception exceptionValue, Object responseValue) {
        this.sequenceId = sequenceId;
        this.exceptionValue = exceptionValue;
        this.responseValue = responseValue;
    }
}
