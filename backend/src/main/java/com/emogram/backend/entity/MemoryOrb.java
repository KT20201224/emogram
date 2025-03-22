package com.emogram.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MemoryOrb - 감정 기록 엔티티 클래스 (MongoDB 저장)
 */
@Document(collection = "memory_orbs")  // MongoDB의 컬렉션 이름을 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoryOrb {

    @Id
    private String id;  // MongoDB에서 자동으로 생성되는 고유 ID

    private Long userId;  // 감정을 기록한 사용자 ID (PostgreSQL의 User ID)

    private Long emotionTypeId;  // 연관된 감정 유형 ID (PostgreSQL의 EmotionType ID)

    private String description;  // 감정에 대한 설명

    private LocalDateTime timestamp;  // 감정 기록 시간

}