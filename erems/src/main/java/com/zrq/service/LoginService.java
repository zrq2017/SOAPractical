package com.zrq.service;

import com.zrq.dao.examinee.ExamineeDao;
import com.zrq.entity.ResResult;
import com.zrq.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        url+="/user/findUserByUsernameAndPassword";
        ResponseEntity<ResResult> response = this.restTemplate.postForEntity(url, user, ResResult.class);
        result=(User)response.getBody().getData();
        return result;
    }

    public Integer registUser(User user) {
        return examineeDao.registUser(user);
    }

    public Integer saveUserImage(String fileName,Integer id) {
        return examineeDao.saveUserImage(fileName,id);
    }

    public User findUserById(Integer id) {
        return examineeDao.findById(id);
    }
}
