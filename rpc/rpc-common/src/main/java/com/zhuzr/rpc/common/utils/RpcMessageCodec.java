package com.zhuzr.rpc.common.utils;

import com.zhuzr.rpc.common.pojo.RpcMessage;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import com.zhuzr.rpc.common.utils.serializer.Serializer;
import com.zhuzr.rpc.common.utils.serializer.impl.JavaSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

public class RpcMessageCodec extends ByteToMessageCodec<RpcMessage> {

    private final Serializer serializer = new JavaSerializer();

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        System.out.println("start encoding");
        // 编码消息类型和序列ID
        out.writeInt(msg.getMessageType());

        // out.writeInt(msg.getSequenceId());
        // 序列化消息体
        System.out.println(msg);
        byte[] body = serializer.serialize(msg);
        System.out.println(Arrays.toString(body));
        out.writeInt(body.length);
        out.writeBytes(body);
        System.out.println(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("start decoding");
        // 确保有足够的字节来读取基础信息
        if (in.readableBytes() < 8) {
            return;
        }

        // 读取消息类型和序列ID
        int messageType = in.readInt();
        // int sequenceId = in.readInt();

        // 确保有足够的字节来读取消息体长度
        if (in.readableBytes() < 4) {
            return;
        }

        // 读取消息体长度
        int bodyLength = in.readInt();

        // 确保有足够的字节来读取消息体
        if (in.readableBytes() < bodyLength) {
            return;
        }

        byte[] bodyBytes = new byte[bodyLength];
        in.readBytes(bodyBytes);

        RpcMessage msg = null;
        if (messageType == RpcMessage.RPC_MESSAGE_TYPE_REQUEST) {
            msg = serializer.deserialize(bodyBytes, RpcRequestMessage.class);
        } else if (messageType == RpcMessage.RPC_MESSAGE_TYPE_RESPONSE) {
            msg = serializer.deserialize(bodyBytes, RpcResponseMessage.class);
        }

        if (msg != null) {
            // msg.setSequenceId(sequenceId);
            out.add(msg);
        }
    }
}
