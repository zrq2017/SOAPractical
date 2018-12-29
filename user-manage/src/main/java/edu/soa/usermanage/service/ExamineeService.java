package edu.soa.usermanage.service;

import edu.soa.usermanage.dao.ExamDao;
import edu.soa.usermanage.dao.ExamineeDao;
import edu.soa.usermanage.entity.Examinee;
import edu.soa.usermanage.entity.MyExam;
import edu.soa.usermanage.entity.User;
import edu.soa.usermanage.util.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zrq on 2018-4-18.
 */
@Service
public class ExamineeService {
    @Autowired
    private ExamineeDao examineeDao;
    @Autowired
    private ExamDao examDao;

    public List<Examinee> findAll(){
        return examineeDao.findAll();
    }

    public User findUserById(Integer id) {
        return examineeDao.findById(id);
    }
    /**
     * 插入一条考生报名考试信息
     * @param userId
     * @param examId
     * @return
     */
    public int insertExam(Integer userId, Integer examId){
        return examDao.insertExam(userId,examId);
    };

    /**
     * 返回所有个人考试信息
     * @return
     */
    public ResResult findMyExam(){
        List<MyExam> list=null;
        try{
            list=examineeDao.findMyExam();
        }catch(Exception e){
            return ResResult.error(300, "返回所有个人考试信息失败！");
        }
        return ResResult.ok().withData(list);
    }

    /**
     * 根据考生及考试更改支付信息
     * @param userId
     * @param examId
     * @return
     */
    public MyExam payByUserAndExam(Integer userId, Integer examId) {
        MyExam myExam=examineeDao.findByUserAndExam(userId, examId);
        examineeDao.updateMyExamPay(myExam);
        //由上一步更新代表已成功修改支付状态
        // 但是由于先查询了原先未修改状态时的数据，故自我更新
        myExam.setPay(1);
        return myExam;
    }

    /**
     * 根据用户查询已考试信息
     * @param userId
     * @return
     */
    public List<MyExam> findByUserAndExamed(Integer userId) {
        return examineeDao.findByUserAndExamed(userId);
    }

    /**
     *查询单项考试个人成绩
     * @param userId
     * @param examId
     * @return
     */
    public List<MyExam> findOneByUserAndExamed(Integer userId, Integer examId) {
        return examineeDao.findOneByUserAndExamed(userId,examId);
    }
    /**
     * 根据考生及支付状态查询
     * @param userId
     * @param pay
     * @return
     */
    public List<MyExam> findByUserAndPay(Integer userId, Integer pay) {
        return examineeDao.findByUserAndPay(userId,pay);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public int updateUser(User user) {
       return examineeDao.updateUser(user);
    }

    /**
     * 根据考试id查询已报名考生信息
     * @param examId
     * @return
     */
    public ResResult findMyExamByExamId(Integer examId){
        List<MyExam> list=null;
        try{
            list=examineeDao.findMyExamByExamId(examId);
        }catch(Exception e){
            return ResResult.error(300, "根据考试id查询已报名考生信息失败！");
        }
        return ResResult.ok().withData(list);
    }
}
