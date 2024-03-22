package com.zhuzr.rpc.common.pojo;

import com.zhuzr.rpc.common.config.RpcMessageCodecConfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class RpcMessage implements Serializable {

    // public static final int RPC_SERIALIZER_ALGORITHM = RpcMessageCodecConfig.GSON;
    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int RPC_MESSAGE_TYPE_RESPONSE = 102;

    private int serializeAlgorithm = RpcMessageCodecConfig.GSON;
    private int messageType;

    public RpcMessage() {
    }

    public abstract int getMessageType();

    public int getSerializeAlgorithm() {
        return serializeAlgorithm;
    }
}
