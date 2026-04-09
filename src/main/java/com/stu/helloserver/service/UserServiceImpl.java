package com.stu.helloserver.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.model.entity.User;
import com.stu.helloserver.model.dto.UserDTO;
//import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.helloserver.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        if(dbUser != null ){
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        userMapper.insert(user);

        return Result.success("注册成功！");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
        if(dbUser == null){
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        if(!dbUser.getPassword().equals(userDTO.getPassword())){
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        return  Result.success("登录成功");
    }

    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        return Result.success("查询成功，用户：" + user.getUsername());
    }

    @Override
    public Result<Object> getUserPage(int pageNum,int pageSize){
        Page<User> pageParam = new Page<>(pageNum,pageSize);
        Page<User> resultPage = userMapper.selectPage(pageParam,null);
        return Result.success(resultPage);
    }



}