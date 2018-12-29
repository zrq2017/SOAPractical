package edu.soa.examservice.controller;

import edu.soa.examservice.entity.Exam;
import edu.soa.examservice.entity.MyExam;
import edu.soa.examservice.entity.ResResult;
import edu.soa.examservice.entity.User;
import edu.soa.examservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zrq on 2018-12-29.
 */
@RestController
@RequestMapping("score")
public class ScoreController {
    @Autowired
    public ScoreService scoreService;

    /**
     * 报名考试
     * @return
     */
    @RequestMapping("signExam")
    public ResResult signExam(User user, Exam exam){
        return scoreService.signExam(user.getId(),exam.getId());
    }

    /**
     * 支付考试
     * @return
     */
    @RequestMapping("payExam")
    public ResResult payExam(Exam exam){
        return scoreService.payExam(exam.getId());
    }

    /**
     * 查个人考试
     * @param uid
     * @return
     */
    @RequestMapping("queryPersonExam")
    public ResResult queryPersonExam(@RequestParam("uid")Integer uid){
        return scoreService.queryPersonExam(uid);
    }

    /**
     * 登记成绩
     * @return
     */
    @RequestMapping("signScore")
    public ResResult signScore(MyExam myExam){
        return scoreService.signScore(myExam);
    }

    /**
     * 修改成绩
     * @return
     */
    @RequestMapping("updatScore")
    public ResResult updatScore(MyExam myExam){
        return scoreService.updatScore(myExam);
    }

    /**
     * 查询单项成绩
     * @return
     */
    @RequestMapping("queryScore")
    public ResResult queryScore(MyExam myExam){
        return scoreService.queryScore(myExam);
    }
}
