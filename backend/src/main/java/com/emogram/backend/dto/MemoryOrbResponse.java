package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemoryOrbResponse {
    private String orbId;
    private String emotionTypeName;
    private String color;
    private String description;
    private LocalDateTime timestamp;
}