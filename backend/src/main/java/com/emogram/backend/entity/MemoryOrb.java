package com.emogram.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "memory_orb")  // ✅ MongoDB 컬렉션 명시
public class MemoryOrb {

    @Id
    private String id;  // ✅ MongoDB의 ID는 String 타입

    private String emotionTypeId;  // ✅ 감정 유형 ID (EmotionType의 ID를 저장)
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;  // ✅ 생성 시간 자동 저장

    public MemoryOrb(String emotionTypeId, String description) {
        this.emotionTypeId = (emotionTypeId != null) ? String.valueOf(emotionTypeId) : null;  // ✅ null 체크 추가
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}