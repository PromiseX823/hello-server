package com.example.controller;
import com.example.common.Result;
import com.example.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") long id){
        int a = 1 / 0;
        return Result.success("查询成功，正在返回 ID 为 " + id + " 的用户信息");
    }
    @PostMapping
    public String createUser(@RequestBody User user){

        return "新增成功,接收到用户: " + user.getName() +",年龄: " +user.getAge();
    }
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @RequestBody User user){
        return "更新成功,ID " + id + " 的用户已修改为: " + user.getName();
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id){

        return "删除成功,已移除 ID 为 " + id + " 的用户";
    }
}
