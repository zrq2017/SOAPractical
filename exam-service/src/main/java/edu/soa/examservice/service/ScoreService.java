package edu.soa.examservice.service;

import edu.soa.examservice.dao.ScoreDao;
import edu.soa.examservice.entity.MyExam;
import edu.soa.examservice.entity.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zrq on 2018-12-29.
 */
@Service
public class ScoreService {
    @Autowired
    public ScoreDao scoreDao;

    /**
     * 报名考试
     * @param uid
     * @param eid
     * @return
     */
    public ResResult signExam(Integer uid, Integer eid) {
        try{
            scoreDao.signExam(uid,eid);
        }catch (Exception e){
            return ResResult.error(300, "报名考试失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 支付考试
     * @param eid
     * @return
     */
    public ResResult payExam(Integer eid) {
        try{
            scoreDao.payExam(eid);
        }catch (Exception e){
            return ResResult.error(300, "支付考试失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 查询个人考试
     * @param uid
     * @return
     */
    public ResResult queryPersonExam(Integer uid) {
        MyExam myExam=null;
        try{
            myExam=scoreDao.queryPersonExam(uid);
        }catch (Exception e){
            return ResResult.error(300, "查询个人考试失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    /**
     *登记成绩
     * @param myExam
     * @return
     */
    public ResResult signScore(MyExam myExam) {
        try{
            myExam=scoreDao.signScore(myExam);
        }catch (Exception e){
            return ResResult.error(300, "登记成绩失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    /**
     * 修改成绩
     * @param myExam
     * @return
     */
    public ResResult updatScore(MyExam myExam) {
        try{
            scoreDao.updatScore(myExam);
        }catch (Exception e){
            return ResResult.error(300, "修改成绩失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    /**
     * 查询单项成绩
     * @param myExam
     * @return
     */
    public ResResult queryScore(MyExam myExam) {
        try{
            myExam=scoreDao.queryScore(myExam);
        }catch (Exception e){
            return ResResult.error(300, "查询单项成绩失败！");
        }
        return ResResult.ok().withData(myExam);
    }
}
