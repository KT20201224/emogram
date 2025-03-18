package com.emogram.backend.service;

import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.EmotionTypeRepository;
import com.emogram.backend.repository.MemoryOrbRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoryOrbService {

    private final MemoryOrbRepository memoryOrbRepository;
    private final EmotionTypeRepository emotionTypeRepository;

    public MemoryOrb saveMemoryOrb(String userId, String emotionType, String description){
        EmotionType type = emotionTypeRepository.findByType(emotionType)
                .orElseThrow(() -> new IllegalArgumentException("Invalid emotion type: " + emotionType));

        MemoryOrb memoryOrb = new MemoryOrb(userId, type, description);

        return memoryOrbRepository.save(memoryOrb);
    }

    public List<MemoryOrb> getUserMemoryOrbs(String userId){
        return memoryOrbRepository.findByUserIdOrderByRecordedAtDesc(userId);
    }

    public List<MemoryOrb> getUserMemoryOrbsByDateRange(String userId, LocalDateTime start, LocalDateTime end){
        return memoryOrbRepository.findByUserIdAndRecordedAtBetween(userId, start, end);
    }
}
