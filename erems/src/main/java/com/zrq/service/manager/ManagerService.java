package com.zrq.service.manager;

import com.zrq.dao.manager.ManagerDao;
import com.zrq.entity.ResResult;
import com.zrq.entity.User;
import com.zrq.service.BaseService;
import com.zrq.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zrq on 2018-5-6.
 */
@Service
public class ManagerService extends BaseService{
    @Autowired
    private ManagerDao managerDao;

    public int addExaminee(User user) {
        Integer result=0;
        String url=this.getBaseUrl("user-service");
        url+="/user/saveUser";
        if(user.getPassword()==null){
            user.setPassword(user.getUsername());
        }
        url+= ConvertUtil.map2Url(user);
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return managerDao.insertExaminee(user);
    }

    /**
     * 根据用户姓名考试号查询
     * @param name
     * @param examId
     * @return
     */
    public List<User> searchByNameAndExam(String name, Integer examId) {
        List<User> l=null;
        l=managerDao.searchByNameAndExam(name,examId);
//        if(l!=null){//设置用户密码为空
//            for (User u:l){
//                u.setPassword("");
//            }
//        }
        return l;
    }
    /**
     * 插入一条考生报名考试信息
     * 返回插入记录主键，需再使用对象的getter方法才可真正得到记录id，否则为记录数，如下：
     * userId=user.getId();
     * @param userId
     * @param examId
     * @return
     */
    public int addExamineeExam(Integer userId, Integer examId) {
        Integer result=0;
        String url=this.getBaseUrl("exam-service");
        url+="/score/signExam?user.id="+userId+"&exam.id="+examId;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return managerDao.insertExam(userId,examId);
    }

    /**
     * 更新考生信息
     * @param user
     */
    public int updateUser(User user) {
        Integer result=0;
        String url=this.getBaseUrl("user-service");
        url+="/user/updateUser";
        url+= ConvertUtil.map2Url(user);
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return managerDao.updateUser(user);
    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public User findUserById(Integer id){
        User result=null;
        String url=this.getBaseUrl("user-service");
        url+="/score/findUserById?id="+id;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=(User)response.getBody().getData();
        }
        return result;
//        return managerDao.findUserById(id);
    }
}
