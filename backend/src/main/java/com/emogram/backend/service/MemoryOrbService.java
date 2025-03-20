package com.emogram.backend.service;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.MemoryOrbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * MemoryOrbService - MemoryOrb 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class MemoryOrbService {

    private final MemoryOrbRepository memoryOrbRepository;

    @Autowired
    public MemoryOrbService(MemoryOrbRepository memoryOrbRepository) {
        this.memoryOrbRepository = memoryOrbRepository;
    }

    /**
     * MemoryOrb 저장하기
     * @param memoryOrb 저장할 MemoryOrb 객체
     * @return 저장된 MemoryOrb
     */
    public MemoryOrb saveMemoryOrb(MemoryOrb memoryOrb) {
        return memoryOrbRepository.save(memoryOrb);
    }

    /**
     * ID로 MemoryOrb 조회하기
     * @param id 조회할 MemoryOrb의 ID
     * @return 조회된 MemoryOrb (Optional)
     */
    public Optional<MemoryOrb> getMemoryOrbById(String id) {
        return memoryOrbRepository.findById(id);
    }

    /**
     * 모든 MemoryOrb 조회하기
     * @return MemoryOrb 리스트
     */
    public List<MemoryOrb> getAllMemoryOrbs() {
        return memoryOrbRepository.findAll();
    }

    /**
     * 특정 감정 유형으로 MemoryOrb 조회하기
     * @param emotionTypeId 감정 유형의 ID
     * @return 조회된 MemoryOrb 리스트
     */
    public List<MemoryOrb> getMemoryOrbsByEmotionTypeId(String emotionTypeId) {
        return memoryOrbRepository.findByEmotionTypeId(emotionTypeId);
    }

    /**
     * MemoryOrb 삭제하기
     * @param id 삭제할 MemoryOrb의 ID
     */
    public void deleteMemoryOrb(String id) {
        memoryOrbRepository.deleteById(id);
    }
}