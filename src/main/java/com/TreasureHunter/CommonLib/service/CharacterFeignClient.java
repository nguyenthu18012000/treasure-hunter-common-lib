package com.TreasureHunter.CommonLib.service;

import com.TreasureHunter.CommonLib.dto.response.character.CharacterRuntimeResponseDTO;
import com.TreasureHunter.CommonLib.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "character-service", path = "/character")
public interface CharacterFeignClient {

    @GetMapping("/state")
    BaseResponse<CharacterRuntimeResponseDTO> getCharacterState(
            @RequestHeader("X-User-Id") Long userId
    );
}
