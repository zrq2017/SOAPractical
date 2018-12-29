package edu.soa.userservice.controller;

import edu.soa.userservice.entity.MyExam;
import edu.soa.userservice.entity.ResResult;
import edu.soa.userservice.entity.Room;
import edu.soa.userservice.entity.User;
import edu.soa.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;


    @RequestMapping("deleteUser")
    public ResResult deleteUser(User user){
        return userService.deleteUser(user);
    }

    @RequestMapping("saveUser")
    public ResResult saveUser(User user){
        return userService.saveUser(user);
    }

    @RequestMapping("user")
    public ResResult user(User user){
        return userService.findUser(user);
    }

    @RequestMapping("num")
    public ResResult num(@RequestParam("id")Integer id){
        return userService.batchCreateExamNum(id);
    }

    /**
     * 保存考试
     * @param myExam
     * @return
     */
    @RequestMapping("saveScore")
    public ResResult saveScore(MyExam myExam){
        return userService.updateScore(myExam);
    }

    /**
     * 查询某项考试考生成绩信息
     * @param sexam 考试id
     * @return
     */
    @RequestMapping("searchExam")
    public ResResult searchExam(@RequestParam(value = "sexam",required = false)Integer sexam){
        return userService.findScoreByExam(sexam);
    }

    @RequestMapping("searchScore")
    public ResResult searchScore(@RequestParam(value = "id",required = false)Integer id) {
        return userService.findScoreById(id);
    }

    /**
     * 删除考点
     * @return
     */
    @RequestMapping("deleteRoom")
    public ResResult deleteRoom(@RequestParam(value = "id")Integer id){
        return userService.deleteRoom(id);
    }

    @RequestMapping("roomlist")
    public ResResult roomList(Room room){
        return userService.searchByNameAndArea(room.getName(),room.getAddress().getId());
    }

    @GetMapping("roomlistAll")
    public ResResult roomlistAll(){
        return userService.searchByNameAndArea(null,null);
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
            rr=userService.updateRoom(room);
        }else {
            rr=userService.saveRoom(room);
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
        return userService.findRoomById(id);
    }


    /**
     * 根据考点名及考点区域查询考生
     * @param name
     * @param area
     * @return
     */
    @RequestMapping("search")
    public ResResult search( @RequestParam(value = "sname",required = false)String name, @RequestParam(value ="sarea",required = false)Integer area){
        return userService.searchByNameAndArea(name,area);
    }
    
}
