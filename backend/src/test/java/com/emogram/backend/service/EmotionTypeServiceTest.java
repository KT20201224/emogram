package com.emogram.backend.service;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.repository.EmotionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EmotionTypeServiceTest {

    @Mock
    private EmotionTypeRepository emotionTypeRepository;

    @InjectMocks
    private EmotionTypeService emotionTypeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("감정 유형 저장 테스트")
    void saveEmotionType(){
        //given
        EmotionType emotion = new EmotionType("기쁨", "#FFD700");
        when(emotionTypeRepository.save(any(EmotionType.class))).thenReturn(emotion);

        //when
        EmotionType savedEmotion = emotionTypeService.saveEmotionType("기쁨", "FFD700");

        //then
        assertNotNull(savedEmotion);
        assertEquals("기쁨", savedEmotion.getType());
        assertEquals("#FFD700", savedEmotion.getColor());
        verify(emotionTypeRepository, times(1)).save(any(EmotionType.class));
    }

    @Test
    @DisplayName("감정 유형 조회 테스트")
    void findEmotionType(){
        //given
        EmotionType emotion = new EmotionType("기쁨", "#FFD700");
        when(emotionTypeRepository.findByType("기쁨")).thenReturn(Optional.of(emotion));

        //when
        EmotionType foundEmotion = emotionTypeService.getEmotionTypeByName("기쁨");

        //then
        assertNotNull(foundEmotion);
        assertEquals("기쁨", foundEmotion.getType());
        assertEquals("#FFD700", foundEmotion.getColor());
        verify(emotionTypeRepository, times(1)).findByType("기쁨");
    }

    @Test
    @DisplayName("새로운 감정 유형을 추가")
    void createEmotionType(){
        //given
        EmotionTypeDto requestDto = new EmotionTypeDto("행복", "FFD700");
        EmotionType savedEntity = new EmotionType("행복", "#FFD700");
        when(emotionTypeRepository.save(any(EmotionType.class))).thenReturn(savedEntity);

        //when
        EmotionTypeDto result = emotionTypeService.createEmotionType(requestDto);

        //then
        assertNotNull(result);
        assertEquals("행복", result.getType());
        assertEquals("#FFD700", result.getColor());
        verify(emotionTypeRepository, times(1)).save(any(EmotionType.class));

    }

    @Test
    @DisplayName("모든 감정 유형을 조회하는 테스트")
    void getAllEmotionTypes() {
        // Given
        List<EmotionType> emotionTypes = List.of(
                new EmotionType("기쁨", "#FFFF00"),
                new EmotionType("슬픔", "#0000FF")
        );

        when(emotionTypeRepository.findAll()).thenReturn(emotionTypes);

        // When
        List<EmotionTypeDto> result = emotionTypeService.getAllEmotionTypes();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("기쁨", result.get(0).getType());
        assertEquals("슬픔", result.get(1).getType());
        verify(emotionTypeRepository, times(1)).findAll();
    }
}