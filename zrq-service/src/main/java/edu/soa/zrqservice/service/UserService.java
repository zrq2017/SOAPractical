package edu.soa.zrqservice.service;

import edu.soa.zrqservice.entity.User;
import edu.soa.zrqservice.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User queryById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

}
