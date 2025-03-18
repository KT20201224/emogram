package com.emogram.backend.repository;

import com.emogram.backend.entity.MemoryOrb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemoryOrbRepository extends MongoRepository<MemoryOrb, String> {

    List<MemoryOrb> findByUserIdOrderByRecordedAtDesc(String userId);
    List<MemoryOrb> findByUserIdAndRecordedAtBetween(String userId, LocalDateTime start, LocalDateTime end);

}
