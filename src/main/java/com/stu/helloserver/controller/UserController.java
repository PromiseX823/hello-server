package com.stu.helloserver.controller;

import org.springframework.web.bind.annotation.*;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") long id){
        String data = "查询成功，正在返回ID为"+ id +" 的用户信息";
        return Result.success(data);
    }
    @PostMapping
    public Result<String> createUser(@RequestBody User user){
        String data = "新增成功,接收到用户: " + user.getName() +",年龄: " +user.getAge();
        return Result.success(data);
    }
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable("id") long id, @RequestBody User user){
        String data = "更新成功,ID " + id + " 的用户已修改为: " + user.getName();
        return Result.success(data);
    }
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") long id){
        String data = "删除成功,已移除 ID 为 " + id + " 的用户";
        return Result.success(data);
    }
}