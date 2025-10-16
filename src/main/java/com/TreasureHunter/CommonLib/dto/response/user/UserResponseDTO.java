package com.TreasureHunter.CommonLib.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String username;

    private LocalDateTime createdAt = LocalDateTime.now();
}
