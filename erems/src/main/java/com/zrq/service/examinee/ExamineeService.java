package com.zrq.service.examinee;

import com.zrq.dao.ExamDao;
import com.zrq.dao.examinee.ExamineeDao;
import com.zrq.entity.MyExam;
import com.zrq.entity.ResResult;
import com.zrq.entity.User;
import com.zrq.entity.examinee.Examinee;
import com.zrq.service.BaseService;
import com.zrq.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zrq on 2018-4-18.
 */
@Service
public class ExamineeService extends BaseService{
    @Autowired
    private ExamineeDao examineeDao;
    @Autowired
    private ExamDao examDao;

    public List<Examinee> findAll(){
        return examineeDao.findAll();
    }

    public User findUserById(Integer id) {
        User result=null;
        String url=this.getBaseUrl("user-service");
        url+="/user/findUserById?id="+id;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=ConvertUtil.parseMap2Object((Map<String, Object>) response.getBody().getData(),User.class);
        }
        return result;
//        return examineeDao.findById(id);
    }
    /**
     * 插入一条考生报名考试信息
     * @param userId
     * @param examId
     * @return
     */
    public int insertExam(Integer userId, Integer examId){
        Integer result=0;
        String url=this.getBaseUrl("exam-service");
        url+="/score/signExam?user.id="+userId+"&exam.id="+examId;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return examDao.insertExam(userId,examId);
    };

    /**
     * 返回所有个人考试信息
     * @return
     */
    public List<MyExam> findMyExam(){
        return examineeDao.findMyExam();
    }

    /**
     * 根据考生及考试更改支付信息
     * @param userId
     * @param examId
     * @return
     */
    public MyExam payByUserAndExam(Integer userId, Integer examId) {
        MyExam result=null;
        String url=this.getBaseUrl("exam-service");
        String url1=url+"/score/payExam?user.id="+userId+"&exam.id="+examId+"&pay=1";
        ResponseEntity<ResResult> response1 = restTemplate.getForEntity(url1,ResResult.class);
        if(response1.getBody().getCode()==200){
            String url2=url+"/score/queryScore?user.id="+userId+"&exam.id="+examId;
            ResponseEntity<ResResult> response2 = restTemplate.getForEntity(url1,ResResult.class);
            result=ConvertUtil.parseMap2Object((Map<String, Object>)response2.getBody().getData(),MyExam.class);
        }
        return result;
//        MyExam myExam=examineeDao.findByUserAndExam(userId, examId);
//        examineeDao.updateMyExamPay(myExam);
//        //由上一步更新代表已成功修改支付状态
//        // 但是由于先查询了原先未修改状态时的数据，故自我更新
//        myExam.setPay(1);
//        return myExam;
    }

    /**
     * 根据用户查询已考试信息
     * @param userId
     * @return
     */
    public List<MyExam> findByUserAndExamed(Integer userId) {
        List<MyExam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/score/findByUserAndExamed?user.id="+userId;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            List list=(List)response.getBody().getData();
            if(list.size()<=0){
                return null;
            }
            result=new ArrayList<MyExam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),MyExam.class));
            }
        }
        return result;
//        return examineeDao.findByUserAndExamed(userId);
    }

    /**
     *查询单项考试个人成绩
     * @param userId
     * @param examId
     * @return
     */
    public List<MyExam> findOneByUserAndExamed(Integer userId, Integer examId) {
        List<MyExam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/score/findByUserAndExamed?user.id="+userId+"&exam.id="+examId;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            List list=(List)response.getBody().getData();
            if(list.size()<=0){
                return null;
            }
            result=new ArrayList<MyExam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),MyExam.class));
            }
        }
        return result;
//        return examineeDao.findOneByUserAndExamed(userId,examId);
    }
    /**
     * 根据考生及支付状态查询
     * @param userId
     * @param pay
     * @return
     */
    public List<MyExam> findByUserAndPay(Integer userId, Integer pay) {
        List<MyExam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/score/findByUserAndExamed?user.id="+userId+"&pay="+pay;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            List list=(List)response.getBody().getData();
            if(list.size()<=0){
                return null;
            }
            result=new ArrayList<MyExam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),MyExam.class));
            }
        }
        return  result;
//        return examineeDao.findByUserAndPay(userId,pay);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public int updateUser(User user) {
        Integer result=0;
        String url=this.getBaseUrl("user-service");
        url+="/user/updateUser";
        url+=ConvertUtil.map2Url(user);
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//       return examineeDao.updateUser(user);
    }

    /**
     * 根据考试id查询已报名考生信息
     * @param examId
     * @return
     */
    public List<MyExam> findMyExamByExamId(Integer examId){
        return examineeDao.findMyExamByExamId(examId);
    }
}
