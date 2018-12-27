package edu.soa.zrqservice.mapper;

import edu.soa.zrqservice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User selectByPrimaryKey(Integer id);
}
