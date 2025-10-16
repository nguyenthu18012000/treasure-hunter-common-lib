package com.TreasureHunter.CommonLib.service;

import com.TreasureHunter.CommonLib.dto.request.auth.LoginRequestDTO;
import com.TreasureHunter.CommonLib.dto.request.auth.RegisterRequestDTO;
import com.TreasureHunter.CommonLib.dto.response.user.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "/user")
public interface UserFeignClient {
    @PostMapping("/register")
    Void register(@RequestBody() RegisterRequestDTO request);

    @PostMapping("/login")
    UserResponseDTO login(@RequestBody() LoginRequestDTO request);
}
