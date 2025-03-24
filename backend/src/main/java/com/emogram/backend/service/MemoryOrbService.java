package com.emogram.backend.service;

import com.emogram.backend.dto.MemoryOrbRequest;
import com.emogram.backend.dto.MemoryOrbResponse;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.EmotionTypeRepository;
import com.emogram.backend.repository.MemoryOrbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemoryOrbService {

    private final MemoryOrbRepository memoryOrbRepository;
    private final EmotionTypeRepository emotionTypeRepository;

    @Autowired
    public MemoryOrbService(MemoryOrbRepository memoryOrbRepository, EmotionTypeRepository emotionTypeRepository) {
        this.memoryOrbRepository = memoryOrbRepository;
        this.emotionTypeRepository = emotionTypeRepository;
    }

    // 1. MemoryOrb 생성
    public MemoryOrbResponse createMemoryOrb(MemoryOrbRequest request) {
        EmotionType emotionType = emotionTypeRepository.findById(request.getEmotionTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 EmotionType ID 입니다."));

        MemoryOrb memoryOrb = MemoryOrb.builder()
                .userId(request.getUserId())
                .emotionTypeId(request.getEmotionTypeId())
                .description(request.getDescription())
                .timestamp(LocalDateTime.now())
                .build();

        memoryOrbRepository.save(memoryOrb);

        return MemoryOrbResponse.builder()
                .orbId(memoryOrb.getId())
                .emotionTypeName(emotionType.getName())
                .color(emotionType.getColor())
                .description(memoryOrb.getDescription())
                .timestamp(memoryOrb.getTimestamp())
                .build();
    }

    // 2. 모든 MemoryOrb 조회 (관리자용)
    public List<MemoryOrbResponse> getAllMemoryOrbs() {
        return memoryOrbRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 3. 특정 사용자 MemoryOrb 조회
    public List<MemoryOrbResponse> getMemoryOrbsByUserId(Long userId) {
        return memoryOrbRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 4. MemoryOrb 수정
    public MemoryOrbResponse updateMemoryOrb(String orbId, MemoryOrbRequest request) {
        MemoryOrb memoryOrb = memoryOrbRepository.findById(orbId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 MemoryOrb ID 입니다."));

        EmotionType emotionType = emotionTypeRepository.findById(request.getEmotionTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 EmotionType ID 입니다."));

        memoryOrb.setEmotionTypeId(request.getEmotionTypeId());
        memoryOrb.setDescription(request.getDescription());
        memoryOrbRepository.save(memoryOrb);

        return convertToResponse(memoryOrb);
    }

    // 5. MemoryOrb 삭제
    public void deleteMemoryOrb(String orbId) {
        memoryOrbRepository.deleteById(orbId);
    }

    // MemoryOrb를 MemoryOrbResponse로 변환하는 메서드
    private MemoryOrbResponse convertToResponse(MemoryOrb memoryOrb) {
        EmotionType emotionType = emotionTypeRepository.findById(memoryOrb.getEmotionTypeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 EmotionType ID 입니다."));

        return MemoryOrbResponse.builder()
                .orbId(memoryOrb.getId())
                .emotionTypeName(emotionType.getName())
                .color(emotionType.getColor())
                .description(memoryOrb.getDescription())
                .timestamp(memoryOrb.getTimestamp())
                .build();
    }

    // 새로운 메서드 추가
    public List<MemoryOrbResponse> getTodayMemoryOrbsByUserId(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN); // 오늘 00:00:00
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);   // 오늘 23:59:59

        List<MemoryOrb> memoryOrbs = memoryOrbRepository.findTodayMemoryOrbsByUserId(userId, startOfDay, endOfDay);

        return memoryOrbs.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}