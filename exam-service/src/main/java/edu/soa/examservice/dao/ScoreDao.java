package edu.soa.examservice.dao;

import edu.soa.examservice.entity.MyExam;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by zrq on 2018-12-29.
 */
@Repository
public interface ScoreDao {

    @Insert("insert myexam(user_id,exam_id,pay) values(#{userId},#{examId},0)")
    public int  signExam(@Param("userId") Integer userId, @Param("examId") Integer examId);

    @Update("update myexam set pay=1 where id=#{id}")
    public int  payExam(@Param("id")Integer eid);


    @Select("select * from myexam where user_id=#{userId}")
    @Results(id="examOnly",value ={
            @Result(property="exam",
                    column = "exam_id",
                    one = @One(select = "edu.soa.examservice.dao.ExamDao.findById")
            ),
            @Result(property="examNum",column = "exam_num"),
            @Result(property="roomNum",column = "room_num")
    })
    public MyExam queryPersonExam(Integer uid);

    @Update("update myexam set score=#{score} where user_id=#{user.id} and exam_id=#{exam.id}")
    public MyExam signScore(MyExam myExam);

    @Update("update myexam set score=#{score} where user_id=#{user.id} and exam_id=#{exam.id}")
    public int updatScore(MyExam myExam);

    @Select("select * from myexam where user_id=#{user.id} and exam_id=#{exam.id}")
    public MyExam queryScore(MyExam myExam);
}
