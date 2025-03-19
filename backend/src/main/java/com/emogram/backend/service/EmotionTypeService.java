package com.emogram.backend.service;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.repository.EmotionTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 감정 유형을 관리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmotionTypeService {

    private final EmotionTypeRepository emotionTypeRepository;

    /**
     * 새로운 감정 유형을 저장하는 메서드
     *
     * @param type 감정 유형명
     * @param color 감정 유형 색상 코드
     * @return 저장된 EmotionType 엔티티
     */
    public EmotionType saveEmotionType(String type, String color) {
        EmotionType emotionType = new EmotionType(type, color);
        return emotionTypeRepository.save(emotionType);
    }

    /**
     * 감정 유형명을 기준으로 감정 유형을 조회하는 메서드
     *
     * @param type 감정 유형명
     * @return 조회된 EmotionType 엔티티
     * @throws RuntimeException 감정 유형이 존재하지 않는 경우 예외 발생
     */
    public EmotionType getEmotionTypeByName(String type) {
        Optional<EmotionType> emotionType = emotionTypeRepository.findByType(type);
        return emotionType.orElseThrow(() -> new RuntimeException("해당 감정 유형이 없습니다 : " + type));
    }

    /**
     * 새로운 감정 유형을 추가하는 메서드 (DTO 기반)
     *
     * @param emotionTypeDto 감정 유형 DTO
     * @return 저장된 감정 유형 DTO
     */
    public EmotionTypeDto createEmotionType(EmotionTypeDto emotionTypeDto) {
        EmotionType emotionType = new EmotionType(emotionTypeDto.getType(), emotionTypeDto.getColor());
        EmotionType savedEmotionType = emotionTypeRepository.save(emotionType);
        return new EmotionTypeDto(savedEmotionType.getType(), savedEmotionType.getColor());
    }

    /**
     * 모든 감정 유형을 조회하는 메서드
     *
     * @return 감정 유형 DTO 리스트
     */
    public List<EmotionTypeDto> getAllEmotionTypes() {
        List<EmotionType> emotionTypes = emotionTypeRepository.findAll();
        return emotionTypes.stream()
                .map(e -> new EmotionTypeDto(e.getType(), e.getColor()))
                .collect(Collectors.toList());
    }
}