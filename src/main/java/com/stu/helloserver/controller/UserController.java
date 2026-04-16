package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.model.dto.UserDTO;
import com.stu.helloserver.entity.UserInfo;
import com.stu.helloserver.vo.UserDetailVO;
import com.stu.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/page")
    public Result<Object> getUserPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize
    ){
        return userService.getUserPage(pageNum,pageSize);
    }

    // 查询用户详情(多表联查+Redis)
    @GetMapping("/{id}/detail")
    public Result<UserDetailVO> getUserDetail(@PathVariable("id") Long userId) {
        return userService.getUserDetail(userId);
    }

    // 更新用户扩展信息
    @PutMapping("/{id}/detail")
    public Result<String> updateUserInfo(@PathVariable("id") Long userId,
                                         @RequestBody UserInfo userInfo) {
        userInfo.setUserId(userId);
        return userService.updateUserInfo(userInfo);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long userId) {
        return userService.deleteUser(userId);
    }

}