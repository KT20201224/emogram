package com.emogram.backend.service;

import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.repository.EmotionTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class EmotionTypeService {

    private final EmotionTypeRepository emotionTypeRepository;

    public EmotionTypeService(EmotionTypeRepository emotionTypeRepository){
        this.emotionTypeRepository = emotionTypeRepository;
    }

    public EmotionType saveEmotionType(String type, String color){
        EmotionType emotionType = new EmotionType(type, color);

        return emotionTypeRepository.save(emotionType);
    }

    public EmotionType getEmotionTypeByName(String type){
        Optional<EmotionType> emotionType = emotionTypeRepository.findByType(type);
        return emotionType.orElseThrow(() -> new RuntimeException("해당 감정 유형이 없습니다 : " + type));
    }
}
