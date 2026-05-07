package com.stu.helloserver.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.stu.helloserver.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
