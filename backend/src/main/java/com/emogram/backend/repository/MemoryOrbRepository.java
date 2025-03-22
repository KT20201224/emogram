package com.emogram.backend.repository;

import com.emogram.backend.entity.MemoryOrb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemoryOrbRepository - MongoDB와 상호작용하는 레포지토리 인터페이스
 */
@Repository
public interface MemoryOrbRepository extends MongoRepository<MemoryOrb, String> {

    /**
     * 사용자 ID로 MemoryOrb 목록 조회
     */
    List<MemoryOrb> findByUserId(Long userId);

    /**
     * 감정 유형 ID로 MemoryOrb 목록 조회
     */
    List<MemoryOrb> findByEmotionTypeId(Long emotionTypeId);

}