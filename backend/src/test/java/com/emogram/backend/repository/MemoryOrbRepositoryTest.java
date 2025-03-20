package com.emogram.backend.repository;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.entity.EmotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest  // ✅ Spring 전체 컨텍스트 로드
@Transactional    // ✅ JPA 테스트를 위해 추가
class MemoryOrbRepositoryTest {

    @Autowired
    private MemoryOrbRepository memoryOrbRepository;

    @Autowired
    private EmotionTypeRepository emotionTypeRepository;  // ✅ 정상적으로 주입됨

    @BeforeEach
    void setUp() {
        memoryOrbRepository.deleteAll();
        emotionTypeRepository.deleteAll();
    }

    @Test
    @DisplayName("MemoryOrb 저장 및 조회 테스트")
    void saveAndFindMemoryOrb() {
        // Given (EmotionType 저장 후 ID 확인)
        EmotionType happiness = new EmotionType("기쁨", "#FFFF00");
        happiness = emotionTypeRepository.save(happiness);

        // Given (MemoryOrb 저장)
        MemoryOrb memoryOrb = new MemoryOrb(String.valueOf(happiness.getId()), "행복한 하루");
        memoryOrbRepository.save(memoryOrb);

        // When (ID를 통해 MemoryOrb 조회)
        Optional<MemoryOrb> foundOrb = memoryOrbRepository.findById(memoryOrb.getId());

        // Then (데이터 검증)
        assertThat(foundOrb).isPresent();
        assertThat(foundOrb.get().getDescription()).isEqualTo("행복한 하루");
        assertThat(foundOrb.get().getEmotionTypeId()).isEqualTo(happiness.getId().toString());
    }

    @Test
    @DisplayName("MemoryOrb 목록 조회 테스트")
    void findAllMemoryOrbs() {
        // Given (EmotionType 저장)
        EmotionType sadness = new EmotionType("슬픔", "#0000FF");
        sadness = emotionTypeRepository.save(sadness);

        // Given (MemoryOrb 2개 저장)
        memoryOrbRepository.save(new MemoryOrb(String.valueOf(sadness.getId()), "슬픈 기억"));
        memoryOrbRepository.save(new MemoryOrb(String.valueOf(sadness.getId()), "조금 우울한 하루"));

        // When (전체 MemoryOrb 조회)
        List<MemoryOrb> orbs = memoryOrbRepository.findAll();

        // Then (데이터 검증)
        assertThat(orbs).hasSize(2);
        assertThat(orbs.get(0).getEmotionTypeId()).isEqualTo(sadness.getId().toString());
    }

    @Test
    @DisplayName("EmotionType으로 MemoryOrb 조회 테스트")
    void findByEmotionTypeTest() {
        // Given (EmotionType 저장)
        EmotionType excitement = new EmotionType("설렘", "#FF69B4");
        excitement = emotionTypeRepository.save(excitement);

        // Given (해당 감정 유형으로 MemoryOrb 저장)
        MemoryOrb memoryOrb1 = new MemoryOrb(String.valueOf(excitement.getId()), "설레는 하루");
        MemoryOrb memoryOrb2 = new MemoryOrb(String.valueOf(excitement.getId()), "기대되는 날");
        memoryOrbRepository.save(memoryOrb1);
        memoryOrbRepository.save(memoryOrb2);

        // When (EmotionTypeId로 MemoryOrb 조회)
        List<MemoryOrb> foundOrbs = memoryOrbRepository.findByEmotionTypeId(excitement.getId().toString());

        // Then (데이터 검증)
        assertThat(foundOrbs).hasSize(2);
        assertThat(foundOrbs.get(0).getEmotionTypeId()).isEqualTo(excitement.getId().toString());
    }
}