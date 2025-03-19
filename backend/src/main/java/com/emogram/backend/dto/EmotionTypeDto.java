package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 감정 유형을 전달하는 DTO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmotionTypeDto {

    /** 감정 유형 이름 */
    private String type;

    /** 감정 유형의 색상 코드 */
    private String color;
}