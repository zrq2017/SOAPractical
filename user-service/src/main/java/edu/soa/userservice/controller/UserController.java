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
@RequestMapping("user")
public class UserController {
    @Autowired
    public UserService userService;


    @RequestMapping("deleteUser")
    public ResResult deleteUser(User user){
        return userService.deleteUser(user);
    }

    /**
     * 生成新用户
     * @param user
     * @return
     */
    @RequestMapping("saveUser")
    public ResResult saveUser(User user){
        return userService.saveUser(user);
    }

    @RequestMapping("updateUser")
    public ResResult updateUser(User user){
        return userService.updateUser(user);
    }

    @RequestMapping("findUserById")
    public ResResult findUserById(Integer id){
        return userService.findUserById(id);
    }

    @RequestMapping("findUserByUsernameAndPassword")
    public ResResult findUserByUsernameAndPassword(User user){
        return userService.findUserByUsernameAndPassword(user);
    }

    /**
     * 根据角色查询用户信息，显示用户列表
     * @param user
     * @return
     */
    @RequestMapping("list")
    public ResResult user(User user){
        if(user!=null && user.getRole()==null){
            user.setRole(2);
        }
        return userService.findUser(user);
    }

    @RequestMapping("num")
    public ResResult num(@RequestParam("id")Integer id){
        return userService.batchCreateExamNum(id);
    }

    /**
     * 保存考试成绩
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
     * 查询考点
     * @param id
     * @return
     */
    @RequestMapping("room")
    public ResResult room(@RequestParam("id")Integer id){
        return userService.findRoomById(id);
    }

    /**
     * 查询地区
     * @return
     */
    @RequestMapping("area")
    public ResResult area(){
        return userService.findArea();
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
