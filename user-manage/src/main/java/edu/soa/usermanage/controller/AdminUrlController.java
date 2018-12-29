package edu.soa.usermanage.controller;

import edu.soa.usermanage.entity.MyExam;
import edu.soa.usermanage.entity.Room;
import edu.soa.usermanage.entity.User;
import edu.soa.usermanage.service.AdminService;
import edu.soa.usermanage.service.ExamineeService;
import edu.soa.usermanage.util.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zrq on 2018-4-21.
 */
@RestController
@RequestMapping("/admin")
public class AdminUrlController {
    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("deleteUser")
    public ResResult deleteUser(User user){
        return adminService.deleteUser(user);
    }

    @RequestMapping("saveUser")
    public ResResult saveUser(User user){
        return adminService.saveUser(user);
    }

    @RequestMapping("user")
    public ResResult user(User user){
        return adminService.findUser(user);
    }

    @RequestMapping("num")
    public ResResult num(@RequestParam("id")Integer id){
        return adminService.batchCreateExamNum(id);
    }

    /**
     * 保存考试
     * @param myExam
     * @return
     */
    @RequestMapping("saveScore")
    public ResResult saveScore(MyExam myExam){
        return adminService.updateScore(myExam);
    }

    /**
     * 查询某项考试考生成绩信息
     * @param sexam 考试id
     * @return
     */
    @RequestMapping("searchExam")
    public ResResult searchExam(@RequestParam(value = "sexam",required = false)Integer sexam){
        return adminService.findScoreByExam(sexam);
    }

    @RequestMapping("searchScore")
    public ResResult searchScore(@RequestParam(value = "id",required = false)Integer id) {
        return adminService.findScoreById(id);
    }

    /**
     * 删除考点
     * @return
     */
    @RequestMapping("deleteRoom")
    public ResResult deleteRoom(@RequestParam(value = "id")Integer id){
        return adminService.deleteRoom(id);
    }

    @RequestMapping("roomlist")
    public ResResult roomList(Room room){
        return adminService.searchByNameAndArea(room.getName(),room.getAddress().getId());
    }

    @GetMapping("roomlistAll")
    public ResResult roomlistAll(){
        return adminService.searchByNameAndArea(null,null);
    }

    /**
     * 更新、新增考点
     * @param room
     * @return
     */
    @RequestMapping("saveRoom")
    public ResResult saveRoom(Room room){
        int tag=0;
        ResResult rr=null;
        if(room.getId()!=null){//id不空即为更新操作
            rr=adminService.updateRoom(room);
        }else {
            rr=adminService.saveRoom(room);
        }
        return rr;
    }

    /**
     * 更新考点页面跳转
     * @param id
     * @return
     */
    @RequestMapping("room")
    public ResResult room(@RequestParam("id")Integer id){
        return adminService.findRoomById(id);
    }


    /**
     * 根据考点名及考点区域查询考生
     * @param name
     * @param area
     * @return
     */
    @RequestMapping("search")
    public ResResult search( @RequestParam(value = "sname",required = false)String name, @RequestParam(value ="sarea",required = false)Integer area){
        return adminService.searchByNameAndArea(name,area);
    }
    /**
     * 查询所有考点区域
     * @return
     */
    @RequestMapping("areaList")
    public ResResult areaList(){
        return adminService.findArea();
    }

    @RequestMapping("examineeInfo")
    public ResResult examineeInfo(@RequestParam(value = "examId",required = false)Integer id,
                               @RequestParam(value = "tag",required = false)Integer tag){
        ResResult rr=null;
        if(id!=null&&!id.equals("")){
           rr=examineeService.findMyExamByExamId(id);
        }else {
            rr=examineeService.findMyExam();
        }
        return rr;
    }
}
