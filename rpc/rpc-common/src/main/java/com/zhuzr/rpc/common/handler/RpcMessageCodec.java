package com.zhuzr.rpc.common.handler;

import com.zhuzr.rpc.common.config.RpcMessageCodecConfig;
import com.zhuzr.rpc.common.pojo.RpcMessage;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.pojo.RpcResponseMessage;
import com.zhuzr.rpc.common.utils.serializer.Serializer;
import com.zhuzr.rpc.common.utils.serializer.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * +   --------  +    -    +   --------   +      --------       +  --------   + ----
 *  magic number | version | message type | serialize algorithm | body length | body;
 */
public class RpcMessageCodec extends ByteToMessageCodec<RpcMessage> {

    private static final Logger logger = Logger.getLogger("RpcMessageCodec");

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMessage msg, ByteBuf out) throws Exception {
        out.writeInt(RpcMessageCodecConfig.MAGIC_NUMBER);
        out.writeByte(RpcMessageCodecConfig.VERSION);
        out.writeInt(msg.getMessageType());
        out.writeInt(msg.getSerializeAlgorithm());

        Serializer serializer = SerializerFactory.getSerializer(msg.getSerializeAlgorithm());
        byte[] body = serializer.serialize(msg);
        out.writeInt(body.length);
        out.writeBytes(body);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 12) {
            logger.info("Not enough space to read basic message.");
            return;
        }

        int magicNumber = in.readInt();
        byte version = in.readByte();
        if (magicNumber != RpcMessageCodecConfig.MAGIC_NUMBER) {
            logger.info("Wrong magic number.");
            return;
        }

        int messageType = in.readInt();
        int serializeAlgorithm = in.readInt();
        int bodyLength = in.readInt();

        if (in.readableBytes() < bodyLength) {
            logger.info("Not enough space to read bodyLength");
            return;
        }
        Serializer serializer = SerializerFactory.getSerializer(serializeAlgorithm);

        byte[] bodyBytes = new byte[bodyLength];
        in.readBytes(bodyBytes);

        RpcMessage msg = null;
        if (messageType == RpcMessage.RPC_MESSAGE_TYPE_REQUEST) {
            msg = serializer.deserialize(bodyBytes, RpcRequestMessage.class);
        } else if (messageType == RpcMessage.RPC_MESSAGE_TYPE_RESPONSE) {
            msg = serializer.deserialize(bodyBytes, RpcResponseMessage.class);
        }

        if (msg != null) {
            out.add(msg);
        }
    }
}
