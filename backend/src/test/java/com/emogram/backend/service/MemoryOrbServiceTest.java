package com.emogram.backend.service;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.repository.MemoryOrbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MemoryOrbServiceTest {

    @Mock
    private MemoryOrbRepository memoryOrbRepository;

    @InjectMocks
    private MemoryOrbService memoryOrbService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("MemoryOrb 저장 테스트")
    void saveMemoryOrb() {
        MemoryOrb memoryOrb = new MemoryOrb("1", "기쁨의 기억");

        when(memoryOrbRepository.save(memoryOrb)).thenReturn(memoryOrb);

        MemoryOrb savedOrb = memoryOrbService.saveMemoryOrb(memoryOrb);

        assertThat(savedOrb).isNotNull();
        assertThat(savedOrb.getDescription()).isEqualTo("기쁨의 기억");
        verify(memoryOrbRepository, times(1)).save(memoryOrb);
    }

    @Test
    @DisplayName("ID로 MemoryOrb 조회 테스트")
    void getMemoryOrbById() {
        MemoryOrb memoryOrb = new MemoryOrb("1", "기쁨의 기억");

        when(memoryOrbRepository.findById("1")).thenReturn(Optional.of(memoryOrb));

        Optional<MemoryOrb> foundOrb = memoryOrbService.getMemoryOrbById("1");

        assertThat(foundOrb).isPresent();
        assertThat(foundOrb.get().getDescription()).isEqualTo("기쁨의 기억");
        verify(memoryOrbRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("전체 MemoryOrb 조회 테스트")
    void getAllMemoryOrbs() {
        MemoryOrb memoryOrb1 = new MemoryOrb("1", "기쁨의 기억");
        MemoryOrb memoryOrb2 = new MemoryOrb("2", "슬픔의 기억");

        when(memoryOrbRepository.findAll()).thenReturn(Arrays.asList(memoryOrb1, memoryOrb2));

        List<MemoryOrb> orbs = memoryOrbService.getAllMemoryOrbs();

        assertThat(orbs).hasSize(2);
        verify(memoryOrbRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("EmotionTypeId로 MemoryOrb 조회 테스트")
    void getMemoryOrbsByEmotionTypeId() {
        MemoryOrb memoryOrb1 = new MemoryOrb("1", "기쁨의 기억");
        MemoryOrb memoryOrb2 = new MemoryOrb("1", "기대의 기억");

        when(memoryOrbRepository.findByEmotionTypeId("1")).thenReturn(Arrays.asList(memoryOrb1, memoryOrb2));

        List<MemoryOrb> orbs = memoryOrbService.getMemoryOrbsByEmotionTypeId("1");

        assertThat(orbs).hasSize(2);
        verify(memoryOrbRepository, times(1)).findByEmotionTypeId("1");
    }

    @Test
    @DisplayName("MemoryOrb 삭제 테스트")
    void deleteMemoryOrb() {
        memoryOrbService.deleteMemoryOrb("1");

        verify(memoryOrbRepository, times(1)).deleteById("1");
    }
}