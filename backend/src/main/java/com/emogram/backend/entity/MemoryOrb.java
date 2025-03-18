package com.emogram.backend.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "memory_orbs")
public class MemoryOrb {

    @Id
    private String id;

    private String userId;
    private EmotionType emotionType;
    private String description;
    private LocalDateTime recordedAt;

    public MemoryOrb(String userId, EmotionType emotionType, String description){
        this.userId = userId;
        this.emotionType = emotionType;
        this.description = description;
        this.recordedAt = LocalDateTime.now();
    }
}
