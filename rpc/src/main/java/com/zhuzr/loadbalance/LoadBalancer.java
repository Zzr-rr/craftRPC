package com.zhuzr.loadbalance;

import com.zhuzr.common.URL;

import java.util.List;
import java.util.Random;

public class LoadBalancer {
    public static URL random(List<URL> urls) {
        Random random = new Random();
        int randomIndex = random.nextInt(urls.size());
        return urls.get(randomIndex);
    }
}
