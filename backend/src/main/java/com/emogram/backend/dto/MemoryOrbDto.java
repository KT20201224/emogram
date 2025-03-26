package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryOrbDto implements Serializable {
    private String orbId;
    private Long userId;
    private String emotionTypeName;
    private String emotionTypeColor;
    private String description;
    private LocalDateTime timestamp;
}