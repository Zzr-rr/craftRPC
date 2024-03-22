package com.zhuzr.rpc.common.Serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.zhuzr.rpc.common.pojo.RpcRequestMessage;
import com.zhuzr.rpc.common.utils.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HessianSerializer {

    public static void main(String[] args) {
        RpcRequestMessage requestMessage = new RpcRequestMessage(1, "hello", "method", String.class, null, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
//        hessianOutput.writeObject(requestMessage);
//        HessianInput hessianInput = new HessianInput(byteArrayInputStream);
//        Object o = hessianInput.readObject();
//        return clazz.cast(o);
    }
}
