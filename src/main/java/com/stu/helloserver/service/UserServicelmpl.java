package com.stu.helloserver.service;
import com.stu.helloserver.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServicelmpl {
    private static final Map<String,String> map = new HashMap<>();

    @Override
    private Result<String> register(UserDTO userDTO) {

    }

}
