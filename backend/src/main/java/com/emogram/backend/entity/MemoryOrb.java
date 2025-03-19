package com.emogram.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * MemoryOrb - 감정 기록을 저장하는 MongoDB 엔티티
 */
@Getter
@NoArgsConstructor
@Document(collection = "memory_orbs") // MongoDB 컬렉션 지정
public class MemoryOrb {

    @Id
    private String id; // MongoDB의 기본 키 (ObjectId)

    private String emotionType; // 감정 유형 (EmotionType의 type 값과 매칭)
    private String description; // 감정에 대한 설명
    private LocalDateTime createdAt; // 생성 시간

    /**
     * MemoryOrb 생성자
     * @param emotionType 감정 유형 (예: "기쁨", "슬픔")
     * @param description 감정에 대한 설명
     */
    public MemoryOrb(String emotionType, String description) {
        this.emotionType = emotionType;
        this.description = description;
        this.createdAt = LocalDateTime.now(); // 자동으로 현재 시간 설정
    }
}