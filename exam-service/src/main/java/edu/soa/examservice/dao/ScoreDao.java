package edu.soa.examservice.dao;

import edu.soa.examservice.entity.MyExam;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zrq on 2018-12-29.
 */
@Repository
public interface ScoreDao {

    @Insert("insert myexam(user_id,exam_id,pay) values(#{userId},#{examId},0)")
    public int  signExam(@Param("userId") Integer userId, @Param("examId") Integer examId);

    @Delete("delete from myexam where user_id=#{userId} and exam_id=#{examId}")
    public int  removeExam(@Param("userId") Integer uid, @Param("examId") Integer eid);

    @Update("update myexam set pay=#{pay} where user_id=#{userId} and exam_id=#{examId}")
    public int  payExam(@Param("userId") Integer uid, @Param("examId") Integer eid,@Param("pay") Integer pay);


    @Select("select * from myexam where user_id=#{userId}")
    @Results(id="examOnly",value ={
            @Result(property="exam",
                    column = "exam_id",
                    one = @One(select = "edu.soa.examservice.dao.ExamDao.findById")
            ),
            @Result(property="examNum",column = "exam_num"),
            @Result(property="roomNum",column = "room_num")
    })
    public List<MyExam> queryPersonExam(Integer uid);

    @Update("update myexam set score=#{score} where user_id=#{user.id} and exam_id=#{exam.id}")
    public int updateScore(MyExam myExam);

    /**
     * 通过考试项id更新个人考试成绩
     * @param myExam
     * @return
     */
    @Update("update myexam set score=#{score} where id=#{id}")
    public int updateScoreByMyExam(MyExam myExam);

    @Select("select * from myexam where user_id=#{user.id} and exam_id=#{exam.id}")
    @ResultMap("examOnly")
    public MyExam queryScore(MyExam myExam);

    @Select("select * from myexam where id=#{id}")
    @ResultMap("examOnly")
    MyExam queryPersonExamByMyExam(Integer id);


    /**
     * 根据某项考试（已付款）查找所有考生成绩
     * @param examId
     * @return
     */
    @Select("<script>"+
            "select * from myexam where pay=1" +
            "<if test='examId!=null and examId != \"\"'>" +
            "and exam_id=#{examId}" +
            "</if>" +
            "</script>")
    @ResultMap("examOnly")
    public List<MyExam> findScoreByExam(@Param("examId") Integer examId);


    /**
     * 查询单项考试个人成绩
     * @param userId
     * @param examId
     * @param score>0
     * @return
     */
    @Select("<script>" +
            "select * from myexam where score>=0" +
            "<if test='${user.id}!=null or ${user.id} != \"\"'>" +
            "and user_id=#{user.id}" +
            "</if>" +
            "<if test='${exam.id}!=null or ${exam.id} != \"\"'>" +
            "and exam_id=#{exam.id}" +
            "</if>" +
            "</script>")
    @ResultMap("examOnly")
    public List<MyExam> findByUserAndExamed(MyExam myExam);

    /**
     * 根据支付状态判断是否支付：0未支付，1支付
     * @param userId
     * @param pay
     * @param score<0
     * @return
     */
    @Select("select * from myexam where score<0 and pay=#{pay} and user_id=#{user.id}")
    @ResultMap("examOnly")
    public List<MyExam> findByUserAndPay(MyExam myExam);

}
