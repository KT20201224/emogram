package com.emogram.backend.service;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.MemoryOrbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemoryOrbServiceTest {

    @Mock
    private MemoryOrbRepository memoryOrbRepository;

    @InjectMocks
    private MemoryOrbService memoryOrbService;

    @BeforeEach
    void setUp() {
        memoryOrbService = new MemoryOrbService(memoryOrbRepository);
    }

    @Test
    @DisplayName("새로운 MemoryOrb 생성 테스트")
    void createMemoryOrbTest() {
        // Given
        MemoryOrb memoryOrb = new MemoryOrb("기쁨", "좋은 일이 생겼다!");
        when(memoryOrbRepository.save(any(MemoryOrb.class))).thenReturn(memoryOrb);

        // When
        MemoryOrb savedOrb = memoryOrbService.createMemoryOrb("기쁨", "좋은 일이 생겼다!");

        // Then
        assertThat(savedOrb).isNotNull();
        assertThat(savedOrb.getEmotionType()).isEqualTo("기쁨");
        assertThat(savedOrb.getDescription()).isEqualTo("좋은 일이 생겼다!");
        verify(memoryOrbRepository, times(1)).save(any(MemoryOrb.class));
    }

    @Test
    @DisplayName("특정 감정 유형의 MemoryOrb 목록 조회 테스트")
    void getMemoryOrbsByEmotionTypeTest() {
        // Given
        List<MemoryOrb> orbs = List.of(
                new MemoryOrb("기쁨", "행복한 날"),
                new MemoryOrb("기쁨", "기분 좋은 아침")
        );
        when(memoryOrbRepository.findByEmotionType("기쁨")).thenReturn(orbs);

        // When
        List<MemoryOrb> foundOrbs = memoryOrbService.getMemoryOrbsByEmotionType("기쁨");

        // Then
        assertThat(foundOrbs).hasSize(2);
        verify(memoryOrbRepository, times(1)).findByEmotionType("기쁨");
    }
}