package com.zhuzr.rpc.common.loadbalance;


import com.zhuzr.rpc.common.pojo.URL;

import java.util.List;
import java.util.Random;

public class LoadBalancer {
    public static URL random(List<URL> serviceUrls) {
        Random random = new Random();
        int randomIndex = random.nextInt(serviceUrls.size());
        return serviceUrls.get(randomIndex);
    }
}
