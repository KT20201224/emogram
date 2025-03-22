package com.emogram.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emotion_types")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // 감정 이름 (예: Happiness, Sadness, Anger 등)

    @Column(nullable = false, unique = true)
    private String color;  // 색깔 (Hex 코드 형태로 저장, 예: #FF0000)

}