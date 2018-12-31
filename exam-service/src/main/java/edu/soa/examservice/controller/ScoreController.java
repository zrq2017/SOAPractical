package edu.soa.examservice.controller;

import edu.soa.examservice.entity.MyExam;
import edu.soa.examservice.entity.ResResult;
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
/**
 * 必传user.id与exam.id
 */
public class ScoreController {
    @Autowired
    public ScoreService scoreService;

    /**
     * 报名考试
     * @return
     */
    @RequestMapping("signExam")
    public ResResult signExam(MyExam myExam){
        return scoreService.signExam(myExam.getUser().getId(),myExam.getExam().getId());
    }

    /**
     * 删除个人考试
     * @return
     */
    @RequestMapping("removeExam")
    public ResResult removeExam(MyExam myExam){
        return scoreService.removeExam(myExam.getUser().getId(),myExam.getExam().getId());
    }

    /**
     * 支付考试
     * @return
     */
    @RequestMapping("payExam")
    public ResResult payExam(MyExam myExam){
        return scoreService.payExam(myExam);
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
    @RequestMapping("updateScore")
    public ResResult updateScore(MyExam myExam){
        return scoreService.updateScore(myExam);
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
