package com.zhuzr.rpc.common.pojo;


public class RpcRequestMessage extends RpcMessage {

    private int requestId;
    // 接口的名称
    private String interfaceName;
    // 方法的名称
    private String methodName;
    // 返回类型
    private Class<?> returnType;
    // 函数参数类型列表
    private Class[] parameterTypes;
    // 函数参数
    private Object[] parameters;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_REQUEST;
    }

    public RpcRequestMessage(int requestId, String interfaceName, String methodName, Class<?> returnType, Class[] parameterTypes, Object[] parameters) {
        this.requestId = requestId;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public RpcRequestMessage(){

    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
}
