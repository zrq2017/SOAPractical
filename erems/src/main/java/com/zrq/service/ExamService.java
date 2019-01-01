package com.zrq.service;

import com.zrq.dao.ExamDao;
import com.zrq.entity.Exam;
import com.zrq.entity.ResResult;
import com.zrq.entity.Statistics;
import com.zrq.util.ConvertUtil;
import com.zrq.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zrq on 2018-4-25.
 */
@Service
public class ExamService extends BaseService{
    @Autowired
    private ExamDao examDao;

    /**
     * 分页查找考试信息
     * @return
     */
    public PageBean<Exam> findByPage(int currentPage, int pageSize){
        List<Exam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/list?page="+currentPage+"&size=pageSize";
        ResponseEntity<List> response = restTemplate.getForEntity(url,List.class);
        if(response.getBody().size()>0){
            List list=(List)response.getBody();
            result=new ArrayList<Exam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),Exam.class));
            }
        }
        List<Exam> list=examDao.findAll();
        Integer totalNum=examDao.count();
        PageBean<Exam> page=new PageBean<Exam>(currentPage,pageSize,totalNum);
        page.setItems(result);
        return page;
    }

    public List<Exam> findAll(){
        List<Exam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/examList";
        ResponseEntity<List> response = restTemplate.getForEntity(url,List.class);
        if(response.getBody().size()>0){
            List list=(List)response.getBody();
            result=new ArrayList<Exam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),Exam.class));
            }
        }
        return result;
//        return examDao.findAll();
    }

    /**
     * 根据考试id查找考试信息
     * @param id
     * @return
     */
    public Exam findById(Integer id) {
        Exam result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/exam?id="+id;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=(Exam)ConvertUtil.parseMap2Object((Map<String, Object>) response.getBody().getData(),Exam.class);
        }
        return result;
//        return examDao.findById(id);
    }

    /**
     * 根据id更新考试信息
     * @param exam
     * @return
     */
    public int updateExam(Exam exam) {
        return saveExam(exam);
//        return examDao.updateExam(exam);
    }

    /**
     * 新增考试信息
     * @param exam
     * @return
     */
    public int saveExam(Exam exam) {
        Integer result=0;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/saveExam";
        url+= ConvertUtil.map2UrlContainTime(exam);
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return examDao.saveExam(exam);
    }

    /**
     * 设置考试是否过期
     * @param id
     * @param outed
     * @return
     */
    public int updateExamOuted(Integer id,Integer outed) {
        Integer result=0;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/out?id="+id+"&out="+outed;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=1;
        }
        return result;
//        return examDao.updateExamOuted(id,outed);
    }

    /**
     * 发现所有过期考试
     * @return
     */
    public List<Exam> findAllOuted() {
        List<Exam> result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/exam/findAllOuted";
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            List list=(List)response.getBody().getData();
            if(list.size()<=0){
                return null;
            }
            result=new ArrayList<Exam>();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                result.add(ConvertUtil.parseMap2Object((Map<String, Object>)it.next(),Exam.class));
            }
        }
        return result;
//        return examDao.findAllOuted();
    }

    /**
     * 统计未过期的考试报名人数数据
     * @return
     */
    public List<Statistics> findRegisterInfo() {
        return examDao.findRegisterInfo();
    }
}
