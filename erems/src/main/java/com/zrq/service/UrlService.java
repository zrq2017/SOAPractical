package com.zrq.service;

import com.zrq.dao.examinee.ExamineeDao;
import com.zrq.entity.MyExam;
import com.zrq.entity.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by zrq on 2018-4-18.
 */
@Service
public class UrlService extends BaseService{
    @Autowired
    private ExamineeDao examineeDao;

    public MyExam findMyexamById(Integer id) {
        MyExam result=null;
        String url=this.getBaseUrl("exam-service");
        url+="/score/queryPersonExamByMyExam?id="+id;
        ResponseEntity<ResResult> response = restTemplate.getForEntity(url,ResResult.class);
        if(response.getBody().getCode()==200){
            result=(MyExam)response.getBody().getData();
        }
        return result;
//        return examineeDao.findMyexamById(id);
    }
}
