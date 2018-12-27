package edu.soa.zrqapp.service;

import edu.soa.zrqapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    public User selectByPrimaryKey(Integer id) {
        //1、 根据user-service获取user-serivce 的集群的信息
        List<ServiceInstance> instances = discoveryClient.getInstances("zrq-service");
        //2、由于我们没有集群，只有一个，所以直接取出第一个
        ServiceInstance instance = instances.get(0);
        //3、拼接URL
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/user/" + id;

        // 使用restTemplate发起请求
        ResponseEntity<User> entity = restTemplate.getForEntity(url, User.class);
        // 获取返回对象
        User user = entity.getBody();
        return user;
    }

    public User queryById(Integer id) {
        return this.selectByPrimaryKey(id);
    }

}
