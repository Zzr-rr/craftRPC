package com.zhuzr.rpc.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class RpcMessage implements Serializable {

    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

    public RpcMessage() {
    }

    private int sequenceId;
    private int messageType; // 0:表示请求消息  1:表示相应消息

    public abstract int getMessageType();
}
