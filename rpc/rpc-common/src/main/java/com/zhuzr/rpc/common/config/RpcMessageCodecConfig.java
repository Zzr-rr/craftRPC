package com.zhuzr.rpc.common.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RpcMessageCodecConfig {

    /**
     * Magic number. Verify RpcMessage
     */
    public static final int HESSIAN = 1;
    public static final int JACKSON = 2;
    public static final int MAGIC_NUMBER = 1234;
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    //version information
    public static final byte VERSION = 1;
    public static final byte TOTAL_LENGTH = 16;
    public static final int HEAD_LENGTH = 16;
    public static final int MAX_FRAME_LENGTH = 8 * 1024 * 1024;
}
