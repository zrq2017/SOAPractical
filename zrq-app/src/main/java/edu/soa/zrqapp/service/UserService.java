package edu.soa.zrqapp.service;

import edu.soa.zrqapp.dao.UserDao;
import edu.soa.zrqapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource(type = UserDao.class)
    private UserDao userDao;

    public User queryById(Integer id) {
        return this.userDao.selectByPrimaryKey(id);
    }

}
