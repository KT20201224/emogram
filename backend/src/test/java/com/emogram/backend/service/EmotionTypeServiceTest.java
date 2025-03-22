package com.emogram.backend.service;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.repository.EmotionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmotionTypeServiceTest {

    @Mock
    private EmotionTypeRepository emotionTypeRepository;

    @InjectMocks
    private EmotionTypeService emotionTypeService;

    private EmotionType testEmotionType;

    @BeforeEach
    void setUp() {
        testEmotionType = new EmotionType(null, "Joy", "#FFD700");
    }

    @Test
    @DisplayName("새로운 감정 유형 저장 테스트")
    void createEmotionType() {
        EmotionTypeDto dto = new EmotionTypeDto("Joy", "#FFD700");

        when(emotionTypeRepository.save(any(EmotionType.class))).thenReturn(testEmotionType);

        EmotionTypeDto savedEmotionType = emotionTypeService.createEmotionType(dto);

        assertNotNull(savedEmotionType);
        assertEquals("Joy", savedEmotionType.getName());
        assertEquals("#FFD700", savedEmotionType.getColor());
        verify(emotionTypeRepository, times(1)).save(any(EmotionType.class));
    }

    @Test
    @DisplayName("모든 감정 유형 조회 테스트")
    void getAllEmotionTypes() {
        when(emotionTypeRepository.findAll()).thenReturn(List.of(testEmotionType));

        List<EmotionTypeDto> emotionTypes = emotionTypeService.getAllEmotionTypes();

        assertNotNull(emotionTypes);
        assertEquals(1, emotionTypes.size());
        assertEquals("Joy", emotionTypes.get(0).getName());
        verify(emotionTypeRepository, times(1)).findAll();
    }
}