package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemoryOrbRequest {
    private Long userId;
    private Long emotionTypeId;
    private String description;
}