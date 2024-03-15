package com.zhuzr.rpc.common.loadbalance;


import com.zhuzr.rpc.common.pojo.ServiceAddress;

import java.util.List;
import java.util.Random;

public class LoadBalancer {
    public static ServiceAddress random(List<ServiceAddress> serviceServiceAddresses) {
        Random random = new Random();
        int randomIndex = random.nextInt(serviceServiceAddresses.size());
        return serviceServiceAddresses.get(randomIndex);
    }
}
