package com.emogram.backend.repository;

import com.emogram.backend.entity.EmotionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // PostgreSQL 사용
public class EmotionTypeRepositoryTest {

    @Autowired EmotionTypeRepository emotionTypeRepository;

    @Test
    @DisplayName("감정 유형 저장")
    void saveAndFindEmotionType(){
        // Given: 새로운 감정 유형을 저장
        EmotionType emotion = new EmotionType("기쁨", "#FFD700");

        // When: 저장 실행
        EmotionType savedEmotion = emotionTypeRepository.save(emotion);

        // Then: 저장된 감정이 정상적으로 반환되었는지 확인
        assertNotNull(savedEmotion);
        assertEquals("기쁨", savedEmotion.getType());
        assertEquals("#FFD700", savedEmotion.getColor());
    }

    @Test
    @DisplayName("감정 유형 조회")
    void findEmotionType(){
        // Given: 미리 저장된 감정 유형
        EmotionType emotion = new EmotionType("기쁨", "#FFD700");
        emotionTypeRepository.save(emotion);

        // When: 저장된 감정을 조회
        Optional<EmotionType> foundEmotion = emotionTypeRepository.findByType("기쁨");

        // Then: 감정이 정상적으로 조회되는지 확인
        assertTrue(foundEmotion.isPresent());
        assertEquals("기쁨", foundEmotion.get().getType());
        assertEquals("#FFD700", foundEmotion.get().getColor());
    }

    @Test()
    @DisplayName("존재하지 않는 감정 조회")
    void findNonExistentEmotionType(){
        //when
        Optional<EmotionType> foundEmotion = emotionTypeRepository.findByType("슬픔");

        //then
        assertFalse(foundEmotion.isPresent());
    }
}
