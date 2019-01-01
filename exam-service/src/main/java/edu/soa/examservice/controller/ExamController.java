package edu.soa.examservice.controller;

import edu.soa.examservice.entity.Exam;
import edu.soa.examservice.entity.ResResult;
import edu.soa.examservice.entity.Statistics;
import edu.soa.examservice.service.ExamService;
import edu.soa.examservice.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zrq on 2018-4-25.
 */
@RestController
@RequestMapping("exam")
public class ExamController{
    //获取配置文件属性
    @Value("${page.size}")
    private Integer pageSize;

    @Autowired
    private ExamService examService;

    @RequestMapping("list")
    public List<Exam> list(@RequestParam(name="page",defaultValue ="1")String currentPage,
                           @RequestParam(name="size",required = false)Integer size){
        if(size!=null){
            pageSize=size;
        }
        PageBean<Exam> examPage=examService.findByPage(Integer.parseInt(currentPage),pageSize);
        return examPage.getItems();
    }

    /**
     * 返回所有考试信息，findALL均为未过期的
     * @return
     */
    @RequestMapping("examList")
    public List<Exam> examList(){
        List<Exam> examList=examService.findAll();
        return examList;
    }

    /**
     * 发现过期考试列表
     * @return
     */
    @RequestMapping("findAllOuted")
    public ResResult findAllOuted(){
        return examService.findAllOuted();
    }

    /**
     * 设置考试是否过期
     * @param id
     * @param out
     * @return
     */
    @RequestMapping("out")
    public ResResult out(@RequestParam(value = "id",required = false)Integer id,@RequestParam("out")Integer out){
        return examService.updateExamOuted(id,out);
    }

    /**
     * 更新、新增考试
     * @param exam
     * @return
     */
    @RequestMapping("saveExam")
    public ResResult saveExam(Exam exam){
        ResResult rr=null;
        if(exam.getId()!=null){//id不空即为更新操作
            rr=examService.updateExam(exam);
        }else {
            rr=examService.saveExam(exam);
        }
        return rr;
    }

    /**
     * 根据id查询考试
     * @param id
     * @return
     */
    @RequestMapping("exam")
    public ResResult exam(@RequestParam("id")Integer id){
        return examService.findById(id);
    }

    /**
     * 返回未过期考试统计信息
     * @return
     */
    @RequestMapping("registInfo")
    public List<Statistics> registInfo(){
        //用exam的描述字段存储报名人数数据
        List<Statistics> statisticsList=examService.findRegisterInfo();
        return statisticsList;
    }

}
