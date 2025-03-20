package com.emogram.backend.controller;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.service.MemoryOrbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class MemoryOrbControllerTest {

    @Mock
    private MemoryOrbService memoryOrbService;

    @InjectMocks
    private MemoryOrbController memoryOrbController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memoryOrbController).build();
    }

    @Test
    @DisplayName("MemoryOrb 생성 테스트")
    void createMemoryOrb() throws Exception {
        MemoryOrb memoryOrb = new MemoryOrb("1", "기쁨의 기억");

        when(memoryOrbService.saveMemoryOrb(any(MemoryOrb.class))).thenReturn(memoryOrb);

        mockMvc.perform(post("/api/memory-orbs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"emotionTypeId\":\"1\", \"description\":\"기쁨의 기억\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("기쁨의 기억"));
    }

    @Test
    @DisplayName("MemoryOrb 조회 테스트")
    void getMemoryOrbById() throws Exception {
        MemoryOrb memoryOrb = new MemoryOrb("1", "기쁨의 기억");

        when(memoryOrbService.getMemoryOrbById("1")).thenReturn(Optional.of(memoryOrb));

        mockMvc.perform(get("/api/memory-orbs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("기쁨의 기억"));
    }

    @Test
    @DisplayName("EmotionTypeId로 MemoryOrb 조회 테스트")
    void getMemoryOrbsByEmotionTypeId() throws Exception {
        MemoryOrb memoryOrb1 = new MemoryOrb("1", "기쁨의 기억");
        MemoryOrb memoryOrb2 = new MemoryOrb("1", "기대의 기억");

        when(memoryOrbService.getMemoryOrbsByEmotionTypeId("1")).thenReturn(Arrays.asList(memoryOrb1, memoryOrb2));

        mockMvc.perform(get("/api/memory-orbs/emotion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("기쁨의 기억"))
                .andExpect(jsonPath("$[1].description").value("기대의 기억"));
    }

    @Test
    @DisplayName("MemoryOrb 삭제 테스트")
    void deleteMemoryOrb() throws Exception {
        doNothing().when(memoryOrbService).deleteMemoryOrb("1");

        mockMvc.perform(delete("/api/memory-orbs/1"))
                .andExpect(status().isOk());

        verify(memoryOrbService, times(1)).deleteMemoryOrb("1");
    }
}