package edu.soa.examservice.service;

import edu.soa.examservice.dao.ScoreDao;
import edu.soa.examservice.entity.MyExam;
import edu.soa.examservice.entity.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 删除个人考试
     * @param uid
     * @param eid
     * @return
     */
    public ResResult removeExam(Integer uid, Integer eid) {
        try{
            scoreDao.removeExam(uid,eid);
        }catch (Exception e){
            return ResResult.error(300, "报名考试失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 支付考试
     * @param myExam
     * @return
     */
    public ResResult payExamPay(MyExam myExam) {
        try{
            myExam.setPay(1);
            this.payExam(myExam);
        }catch (Exception e){
            return ResResult.error(300, "支付考试失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     *
     * 设置考试支付状态
     * @param myExam
     * @return
     */
    public ResResult payExam(MyExam myExam) {
        try{
            scoreDao.payExam(myExam.getUser().getId(),myExam.getExam().getId(),myExam.getPay());
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
        List<MyExam> myExam=null;
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
            scoreDao.updateScore(myExam);
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
    public ResResult updateScore(MyExam myExam) {
        try{
            if(myExam.getId()!=null){
                scoreDao.updateScoreByMyExam(myExam);
            }else {
                scoreDao.updateScore(myExam);
            }
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

    public ResResult queryPersonExamByMyExam(Integer id) {
        MyExam myExam=null;
        try{
            myExam=scoreDao.queryPersonExamByMyExam(id);
        }catch (Exception e){
            return ResResult.error(300, "根据score项id查询个人考试失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    public ResResult findScoreByExam(Integer eid) {
        List<MyExam> myExam=null;
        try{
            myExam=scoreDao.findScoreByExam(eid);
        }catch (Exception e){
            return ResResult.error(300, "根据score项考试id查询考试成绩失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    public ResResult findByUserAndExamed(MyExam myExam) {
        List<MyExam> list=null;
        try{
            list=scoreDao.findByUserAndExamed(myExam);
        }catch (Exception e){
            return ResResult.error(300, "根据score>0项考试id查询考试成绩失败！");
        }
        return ResResult.ok().withData(list);
    }

    public ResResult findByUserAndPay(MyExam myExam) {
        List<MyExam> list=null;
        try{
            list=scoreDao.findByUserAndPay(myExam);
        }catch (Exception e){
            return ResResult.error(300, "根据支付状态判断是否支付：0未支付，1支付失败！");
        }
        return ResResult.ok().withData(list);
    }
}
