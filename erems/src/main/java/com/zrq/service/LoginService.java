package com.zrq.service;

import com.zrq.dao.examinee.ExamineeDao;
import com.zrq.entity.ResResult;
import com.zrq.entity.User;
import com.zrq.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zrq on 2018-4-23.
 */
@Service
public class LoginService extends BaseService{
    @Autowired
    private ExamineeDao examineeDao;

    public User findUser(User user) {
        User result=null;
        String url=this.getBaseUrl("user-service");
//        url+="/user/findUserByUsernameAndPassword";
//        String response = this.restTemplate.postForObject(url, user, String.class);
//        result= (User)response.getBody().getData();
        url+="/user/findUserByUsernameAndPassword?username="+user.getUsername()+"&password="+user.getPassword();
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        result= ConvertUtil.parseMap2Object((Map<String, Object>)response.getBody().getData(),User.class);
        return result;
    }

    public Integer registUser(User user) {
        Integer result=0;
        String url=this.getBaseUrl("user-service");
        url+="/user/saveUser";
        user.setRole(2);//考生注册默认为角色2
        user.setSex('男');//默认为男
        url+=ConvertUtil.map2Url(user);
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
//        return examineeDao.registUser(user);
        return result;
    }

    public Integer saveUserImage(String fileName,Integer id) {
        return examineeDao.saveUserImage(fileName,id);
    }

    public User findUserById(Integer id) {
        User result=null;
        String url=this.getBaseUrl("user-service");
        url+="/user/findUserById?id="+id;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        result= ConvertUtil.parseMap2Object((Map<String, Object>)response.getBody().getData(),User.class);
        return result;
//        return examineeDao.findById(id);
    }
}
