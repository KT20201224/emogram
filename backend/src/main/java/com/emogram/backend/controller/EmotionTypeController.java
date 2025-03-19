package com.emogram.backend.controller;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.service.EmotionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 감정 유형을 관리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/emotion-types")
@RequiredArgsConstructor
public class EmotionTypeController {

    private final EmotionTypeService emotionTypeService;

    /**
     * 새로운 감정 유형을 추가하는 API
     *
     * @param emotionTypeDto 감정 유형 DTO
     * @return 생성된 감정 유형
     */
    @PostMapping
    public ResponseEntity<EmotionTypeDto> createEmotionType(@RequestBody EmotionTypeDto emotionTypeDto) {
        EmotionTypeDto createdEmotionType = emotionTypeService.createEmotionType(emotionTypeDto);
        return ResponseEntity.ok(createdEmotionType);
    }

    /**
     * 모든 감정 유형을 조회하는 API
     *
     * @return 감정 유형 리스트
     */
    @GetMapping
    public ResponseEntity<List<EmotionTypeDto>> getAllEmotionTypes() {
        List<EmotionTypeDto> emotionTypes = emotionTypeService.getAllEmotionTypes();
        return ResponseEntity.ok(emotionTypes);
    }
}