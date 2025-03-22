package com.emogram.backend.service;

import com.emogram.backend.dto.MemoryOrbRequest;
import com.emogram.backend.dto.MemoryOrbResponse;
import com.emogram.backend.entity.EmotionType;
import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.EmotionTypeRepository;
import com.emogram.backend.repository.MemoryOrbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemoryOrbServiceTest {

    @Mock
    private MemoryOrbRepository memoryOrbRepository;

    @Mock
    private EmotionTypeRepository emotionTypeRepository;

    @InjectMocks
    private MemoryOrbService memoryOrbService;

    private EmotionType testEmotionType;
    private MemoryOrb testMemoryOrb;

    @BeforeEach
    void setUp() {
        testEmotionType = new EmotionType(1L, "Joy", "#FFD700");
        testMemoryOrb = MemoryOrb.builder()
                .id("test-orb-id")
                .userId(1L)
                .emotionTypeId(1L)
                .description("Test Description")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("MemoryOrb 생성 테스트")
    void createMemoryOrb() {
        MemoryOrbRequest request = new MemoryOrbRequest(1L, 1L, "Test Description");

        when(emotionTypeRepository.findById(1L)).thenReturn(Optional.of(testEmotionType));
        when(memoryOrbRepository.save(any(MemoryOrb.class))).thenReturn(testMemoryOrb);

        MemoryOrbResponse response = memoryOrbService.createMemoryOrb(request);

        assertThat(response.getEmotionTypeName()).isEqualTo("Joy");
        assertThat(response.getDescription()).isEqualTo("Test Description");

        verify(memoryOrbRepository, times(1)).save(any(MemoryOrb.class));
    }

    @Test
    @DisplayName("모든 MemoryOrb 조회 테스트")
    void getAllMemoryOrbs() {
        when(memoryOrbRepository.findAll()).thenReturn(Collections.singletonList(testMemoryOrb));
        when(emotionTypeRepository.findById(1L)).thenReturn(Optional.of(testEmotionType));

        List<MemoryOrbResponse> responses = memoryOrbService.getAllMemoryOrbs();

        assertThat(responses.size()).isEqualTo(1);
        assertThat(responses.get(0).getEmotionTypeName()).isEqualTo("Joy");

        verify(memoryOrbRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("특정 사용자 MemoryOrb 조회 테스트")
    void getMemoryOrbsByUserId() {
        when(memoryOrbRepository.findByUserId(1L)).thenReturn(Collections.singletonList(testMemoryOrb));
        when(emotionTypeRepository.findById(1L)).thenReturn(Optional.of(testEmotionType));

        List<MemoryOrbResponse> responses = memoryOrbService.getMemoryOrbsByUserId(1L);

        assertThat(responses.size()).isEqualTo(1);
        assertThat(responses.get(0).getEmotionTypeName()).isEqualTo("Joy");

        verify(memoryOrbRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("MemoryOrb 수정 테스트")
    void updateMemoryOrb() {
        MemoryOrbRequest request = new MemoryOrbRequest(1L, 1L, "Updated Description");

        when(memoryOrbRepository.findById("test-orb-id")).thenReturn(Optional.of(testMemoryOrb));
        when(emotionTypeRepository.findById(1L)).thenReturn(Optional.of(testEmotionType));
        when(memoryOrbRepository.save(any(MemoryOrb.class))).thenReturn(testMemoryOrb);

        MemoryOrbResponse response = memoryOrbService.updateMemoryOrb("test-orb-id", request);

        assertThat(response.getDescription()).isEqualTo("Updated Description");

        verify(memoryOrbRepository, times(1)).save(any(MemoryOrb.class));
    }

    @Test
    @DisplayName("MemoryOrb 삭제 테스트")
    void deleteMemoryOrb() {
        doNothing().when(memoryOrbRepository).deleteById("test-orb-id");

        memoryOrbService.deleteMemoryOrb("test-orb-id");

        verify(memoryOrbRepository, times(1)).deleteById("test-orb-id");
    }
}