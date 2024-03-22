package com.zhuzr.rpc.common.utils;

import com.zhuzr.rpc.common.config.RpcMessageCodecConfig;
import com.zhuzr.rpc.common.pojo.RpcMessage;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import com.zhuzr.rpc.common.utils.serializer.Serializer;
import com.zhuzr.rpc.common.utils.serializer.SerializerFactory;
import com.zhuzr.rpc.common.utils.serializer.impl.GsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.Arrays;
import java.util.List;

/**
 * ----         -            ----
 * magic code    version     message type
 */
public class RpcMessageCodec extends ByteToMessageCodec<RpcMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        System.out.println("start encoding");
        // 编码消息类型 + 序列化算法
        out.writeInt(RpcMessageCodecConfig.MAGIC_NUMBER);
        out.writeByte(RpcMessageCodecConfig.VERSION);
        out.writeInt(msg.getMessageType());
        System.out.println(msg.getMessageType());
        out.writeInt(msg.getSerializeAlgorithm());
        // 序列化消息体
        Serializer serializer = SerializerFactory.getSerializer(msg.getSerializeAlgorithm());
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

        // 确保有足够的字节来读取基础信息 + 消息体长度
        if (in.readableBytes() < 12) {
            return;
        }

        // 读取消息类型和序列ID
        int magicNumber = in.readInt();
        if (magicNumber != RpcMessageCodecConfig.MAGIC_NUMBER) {
            System.out.println("not correct magic number");
            return;
        }

        byte version = in.readByte();
        int messageType = in.readInt();
        int serializeAlgorithm = in.readInt(); // 读取序列化算法
        int bodyLength = in.readInt();

        if (in.readableBytes() < bodyLength) {
            return;
        }
        Serializer serializer = SerializerFactory.getSerializer(serializeAlgorithm);

        byte[] bodyBytes = new byte[bodyLength];
        in.readBytes(bodyBytes);

        RpcMessage msg = null;
        if (messageType == RpcMessage.RPC_MESSAGE_TYPE_REQUEST) {
            System.out.println("request");
            msg = serializer.deserialize(bodyBytes, RpcRequestMessage.class);
        } else if (messageType == RpcMessage.RPC_MESSAGE_TYPE_RESPONSE) {
            System.out.println("response");
            msg = serializer.deserialize(bodyBytes, RpcResponseMessage.class);
        }

        if (msg != null) {
            // msg.setSequenceId(sequenceId);
            out.add(msg);
        }
    }
}
