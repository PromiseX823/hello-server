package com.stu.helloserver.service;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.entity.UserInfo;
import com.stu.helloserver.model.dto.UserDTO;
import com.stu.helloserver.vo.UserDetailVO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);
    Result<Object> getUserPage(int pageNum,int pageSize);
    Result<UserDetailVO> getUserDetail(Long userId);
    Result<String> updateUserInfo(UserInfo userInfo);
    Result<String> deleteUser(Long userId);

}
