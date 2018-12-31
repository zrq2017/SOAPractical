package com.zrq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BaseService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    protected RestTemplate restTemplate;

    public String getBaseUrl(String service){
        //1、 根据user-service获取user-serivce 的集群的信息
        List<ServiceInstance> instances = discoveryClient.getInstances(service);
        //2、由于我们没有集群，只有一个，所以直接取出第一个
        ServiceInstance instance = instances.get(0);
        //3、拼接URL
        String url = "http://" + instance.getHost() + ":" + instance.getPort();
        return url;
    }

}
