package edu.soa.userservice.service;

import edu.soa.userservice.dao.UserDao;
import edu.soa.userservice.entity.*;
import edu.soa.userservice.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;


    /**
     * 根据考点名及考试区域查询
     * @param name
     * @param area
     * @return
     */
    public ResResult searchByNameAndArea(String name, Integer area) {
        List<Room> list=null;
        try{
            list=userDao.searchByNameAndArea(name,area);
        }catch(Exception e){
            return ResResult.error(300, "根据考点名及考试区域查询失败！");
        }
        return ResResult.ok().withData(list);
    }

    /**
     * 根据id查找考点信息
     * @param id
     * @return
     */
    public ResResult findRoomById(Integer id) {
        Room r=null;
        try {
            r= userDao.findRoomById(id);
        }catch(Exception e){
            return ResResult.error(300, "根据id查找考点信息失败！");
        }
        return ResResult.ok().withData(r);
    }

    /**
     * 根据id更新考点信息
     * @param room
     * @return
     */
    public ResResult updateRoom(Room room) {
        try {
            if(userDao.updateRoom(room)<=0){
                return ResResult.error(300, "根据id更新考点信息失败！");
            }
        }catch(Exception e){
            return ResResult.error(300, "根据id更新考点信息失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 新增考点信息
     * @param room
     * @return
     */
    public ResResult saveRoom(Room room) {
        try {
            if(userDao.saveRoom(room)<=0){
                return ResResult.error(300, "新增考点信息失败！");
            }
        }catch(Exception e){
            return ResResult.error(300, "新增考点信息失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 删除考点
     * @param room
     * @return
     */
    public ResResult deleteRoom(Integer id) {
        try {
            userDao.deleteRoom(id);
        }catch(Exception e){
            return ResResult.error(300, "删除考点失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 根据某项考试查找所有考生成绩
     * @param sexam
     * @return
     */
    public ResResult findScoreByExam(Integer sexam) {
        List<MyExam> list=null;
        try {
            list=userDao.findScoreByExam(sexam);
        }catch(Exception e){
            return ResResult.error(300, "根据某项考试查找所有考生成绩失败！");
        }
        return ResResult.ok().withData(list);
    }

    /**
     * 根据个人考试成绩项id查询单条信息
     * @param id
     * @return
     */
    public ResResult findScoreById(Integer id) {
        MyExam myExam=null;
        try{
            myExam=userDao.findScoreById(id);
        }catch (Exception e){
            return ResResult.error(300, "根据个人考试成绩项id查询单条信息失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    /**
     * 更新个人考试成绩
     * @param myExam
     * @return
     */
    public ResResult updateScore(MyExam myExam) {
        try {
            userDao.updateScore(myExam);
        }catch(Exception e){
            return ResResult.error(300, "更新个人考试成绩失败！");
        }
        return ResResult.ok().withData(myExam);
    }

    /**
     * 批量生成某考试的考号
     * @return
     */
    public ResResult batchCreateExamNum(Integer examId) {
        try {
            List<MyExam> e = userDao.findScoreByExam(examId);
            //创建分地区存储考生信息图
            Map<Integer, List<MyExam>> areaMap = new TreeMap<Integer, List<MyExam>>();
            //创建分地区编号图----可不用
            Map<Integer, String> areaNumMap = new TreeMap<Integer, String>();
            //创建考点信息图
            Map<Integer, List<Room>> examMap = new TreeMap<Integer, List<Room>>();
            System.out.println("某考试的考生记录:" + e.size());
            /**
             * 步骤1：建立以地区为分类的不同地区考生列表
             * 1.分地区存储考生信息图
             * 2.分地区编号图
             */
            for (MyExam m : e) {//循环遍历获得的某考试的考生记录，按地区划分考生为不同列表
                Integer id = m.getUser().getAddress().getId();
                if (areaMap.containsKey(id)) {
                    areaMap.get(id).add(m);
                } else {
                    List<MyExam> l = new ArrayList<MyExam>();
                    l.add(m);
                    areaMap.put(id, l);
                    areaNumMap.put(id, m.getAddress().getNum());
                }
            }
            System.out.println("xxx:" + areaMap);
            System.out.println("分地区考生信息图:" + areaMap.keySet());
            /**
             * 步骤2：按地区获得考点信息图
             */
            for (Integer s : areaMap.keySet()) {
                List<Room> el = userDao.searchByNameAndArea(null, s);
                examMap.put(s, el);
            }
            System.out.println("考点信息图:" + examMap);
            /**
             * 步骤3：对应地区考生考号分配（单地区考点位置数量一定大于该地区考生总数）
             * 1.每个考室大小为30
             * 2.获得某地区考生记录
             * 3.根据获得的考点信息安排考生
             */
            for (Map.Entry<Integer, List<MyExam>> ml : areaMap.entrySet()) {//分地区考生遍历
                Integer id = ml.getKey();
                List<MyExam> tempUser = ml.getValue();
                int tsize = tempUser.size();
                List<Room> tempRoom = examMap.get(id);
                int i = 0, count = 0;//i考点序号增加，count记录当前已编码考生数
                outer:
                while (true) {//跳出循环标号，已尝试该循环只能跳到for(两层)，无法跳出while
                    for (int j = 1; j < tempRoom.get(i).getSize(); j++) {
                        String rnum = StringUtil.changeToString(j, 2);//考点考室编号
                        for (int k = 1; k <= 30; k++) {
                            String pnum = StringUtil.changeToString(k, 2);//位置编号
                            tempUser.get(count).setExamNum(tempRoom.get(i).getAddress().getNum() +
                                    tempRoom.get(0).getNum() + rnum + pnum);
                            tempUser.get(count).setRoomNum(tempRoom.get(i).getAddress().getNum() +
                                    tempRoom.get(0).getNum() + rnum);
                            count++;
                            if (count >= tsize) {
                                break outer;
                            }
                        }
                    }
                    i++;//此处可优化判断是否需要增加考点，或初始设计逻辑自动检测需要的考点
                }
            }
            for (MyExam ee : e) {
                System.out.println("考号列表：" + ee.getExamNum() + ee.getUser().getName());
            }
            int x = userDao.batchCreateExamNum(e);
        }catch(Exception e){
            return ResResult.error(300, "生成考号失败！");
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 查找用户列表
     * @param user
     * @return
     */
    public ResResult findUser(User user) {
        List<User> list=null;
        try {
            list=userDao.findUser(user);
        }catch(Exception e){
            return ResResult.error(300, "查找用户列表失败！");
        }
        return ResResult.ok().withData(list);
    }

    /**
     * 生成新用户
     * @param user
     * @return
     */
    public ResResult saveUser(User user) {
        try {
            userDao.saveUser(user);
        }catch(Exception e){
            return ResResult.error(300, "保存用户数据失败！");
        }
        return ResResult.ok().withData(user);
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    public ResResult deleteUser(User user) {
        try {
            userDao.deleteUser(user);
        }catch(Exception e){
            return ResResult.error(300, "删除用户数据失败！");
        }
        return ResResult.ok().withData(user);
    }

    /**
     * 根据用户id查找某用户
     * @param id
     * @return
     */
    public ResResult findUserById(Integer id) {
        User user=null;
        try {
            user=userDao.findUserById(id);
        }catch(Exception e){
            return ResResult.error(300, "根据用户id查找某用户数据失败！");
        }
        return ResResult.ok().withData(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public ResResult updateUser(User user) {
        try {
            userDao.updateUser(user);
        }catch(Exception e){
            return ResResult.error(300, "更新用户信息失败！");
        }
        return ResResult.ok().withData(user);
    }

    /**
     * 根据用户名密码查询用户
     * @param user
     * @return
     */
    public ResResult findUserByUsernameAndPassword(User user) {
        try {
            user=userDao.findUserByUsernameAndPassword(user);
        }catch(Exception e){
            return ResResult.error(300, "用户不存在！");
        }
        return ResResult.ok("成功").withData(user);
    }

    /**
     * 查询所有地区
     * @return
     */
    public ResResult findArea() {
        List<Address> list=null;
        try {
            list=userDao.findArea();
        }catch(Exception e){
            return ResResult.error(300, "查询所有地区失败！");
        }
        return ResResult.ok("成功").withData(list);
    }
}
