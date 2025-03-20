package com.emogram.backend.repository;

import com.emogram.backend.entity.MemoryOrb;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemoryOrbRepository extends MongoRepository<MemoryOrb, String> {  // ✅ ID 타입은 String
    List<MemoryOrb> findByEmotionTypeId(String emotionTypeId);  // ✅ 감정 유형 ID도 String
}