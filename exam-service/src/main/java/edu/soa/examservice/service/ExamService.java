package edu.soa.examservice.service;

import com.github.pagehelper.PageHelper;
import edu.soa.examservice.dao.ExamDao;
import edu.soa.examservice.entity.Exam;
import edu.soa.examservice.entity.ResResult;
import edu.soa.examservice.entity.Statistics;
import edu.soa.examservice.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zrq on 2018-4-25.
 */
@Service
public class ExamService {
    @Autowired
    private ExamDao examDao;

    /**
     * 分页查找考试信息
     * @return
     */
    public PageBean<Exam> findByPage(int currentPage, int pageSize){
        PageHelper.startPage(currentPage,pageSize);
        List<Exam> list=examDao.findAll();
        Integer totalNum=examDao.count();
        PageBean<Exam> page=new PageBean<Exam>(currentPage,pageSize,totalNum);
        page.setItems(list);
        return page;
    }

    public List<Exam> findAll(){
        return examDao.findAll();
    }

    /**
     * 根据考试id查找考试信息
     * @param id
     * @return
     */
    public ResResult findById(Integer id) {
        Exam exam=null;
        try{
            exam=examDao.findById(id);
        }catch (Exception e){
            return ResResult.error(300, "根据考试id查找考试信息失败！");
        }
        return ResResult.ok().withData(exam);
    }


    /**
     * 根据id更新考试信息
     * @param exam
     * @return
     */
    public ResResult updateExam(Exam exam) {
        try{
            examDao.updateExam(exam);
        }catch (Exception e){
            return ResResult.error(300, "根据id更新考试信息失败！");
        }
        return ResResult.ok().withData(exam);
    }

    /**
     * 新增考试信息
     * @param exam
     * @return
     */
    public ResResult saveExam(Exam exam) {
        try{
            examDao.saveExam(exam);
        }catch (Exception e){
            return ResResult.error(300, "新增考试信息失败！");
        }
        return ResResult.ok().withData(exam);
    }

    /**
     * 设置考试是否过期
     * @param id
     * @param outed
     * @return
     */
    public ResResult updateExamOuted(Integer id, Integer outed) {
        try{
            examDao.updateExamOuted(id,outed);
        }catch (Exception e){
            return ResResult.error(300, "设置考试过期失败！");
        }
        return ResResult.ok("设置是否考试过期成功！").withData(Boolean.TRUE);
    }

    /**
     * 发现所有过期考试
     * @return
     */
    public ResResult findAllOuted() {
        List<Exam> list=null;
        try{
            list=examDao.findAllOuted();
        }catch(Exception e){
            return ResResult.error(300, "发现所有过期考试失败！");
        }
        return ResResult.ok().withData(list);
    }

    /**
     * 统计未过期的考试报名人数数据
     * @return
     */
    public List<Statistics> findRegisterInfo() {
        return examDao.findRegisterInfo();
    }
}
