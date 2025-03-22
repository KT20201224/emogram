package com.emogram.backend.service;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.repository.EmotionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmotionTypeService {

    private final EmotionTypeRepository emotionTypeRepository;

    @Autowired
    public EmotionTypeService(EmotionTypeRepository emotionTypeRepository) {
        this.emotionTypeRepository = emotionTypeRepository;
    }

    /**
     * 새로운 EmotionType 추가 (관리자용)
     * 기존 캐시를 무효화하여 최신 데이터를 반영
     */
    @CacheEvict(value = "emotionTypeCache", allEntries = true)
    public EmotionTypeDto createEmotionType(EmotionTypeDto dto) {
        if (emotionTypeRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 감정 이름입니다.");
        }

        if (emotionTypeRepository.findByColor(dto.getColor()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 색상입니다.");
        }

        EmotionType emotionType = EmotionType.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .build();

        emotionTypeRepository.save(emotionType);

        return dto;
    }

    /**
     * 모든 EmotionType 목록 조회
     * 캐시가 있는 경우 캐시 데이터를 반환하고, 없으면 DB에서 조회 후 캐시에 저장
     */
    @Cacheable(value = "emotionTypeCache")
    public List<EmotionTypeDto> getAllEmotionTypes() {
        return emotionTypeRepository.findAll().stream()
                .map(emotionType -> new EmotionTypeDto(emotionType.getName(), emotionType.getColor()))
                .collect(Collectors.toList());
    }
}