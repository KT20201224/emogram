package com.emogram.backend.controller;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.service.MemoryOrbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * MemoryOrbController
 * MemoryOrb 관련 요청을 처리하는 컨트롤러 클래스.
 */
@RestController
@RequestMapping("/api/memory-orbs")
public class MemoryOrbController {

    private final MemoryOrbService memoryOrbService;

    @Autowired
    public MemoryOrbController(MemoryOrbService memoryOrbService) {
        this.memoryOrbService = memoryOrbService;
    }

    /**
     * 새로운 MemoryOrb를 저장한다.
     * @param memoryOrb 저장할 MemoryOrb 객체
     * @return 저장된 MemoryOrb 객체
     */
    @PostMapping
    public MemoryOrb createMemoryOrb(@RequestBody MemoryOrb memoryOrb) {
        return memoryOrbService.saveMemoryOrb(memoryOrb);
    }

    /**
     * 특정 ID로 MemoryOrb를 조회한다.
     * @param id 조회할 MemoryOrb의 ID
     * @return 조회된 MemoryOrb 객체
     */
    @GetMapping("/{id}")
    public Optional<MemoryOrb> getMemoryOrbById(@PathVariable String id) {
        return memoryOrbService.getMemoryOrbById(id);
    }

    /**
     * 모든 MemoryOrb를 조회한다.
     * @return 모든 MemoryOrb 리스트
     */
    @GetMapping
    public List<MemoryOrb> getAllMemoryOrbs() {
        return memoryOrbService.getAllMemoryOrbs();
    }

    /**
     * 특정 EmotionTypeId로 MemoryOrb를 조회한다.
     * @param emotionTypeId 감정 유형 ID
     * @return 해당 감정 유형의 MemoryOrb 리스트
     */
    @GetMapping("/emotion/{emotionTypeId}")
    public List<MemoryOrb> getMemoryOrbsByEmotionTypeId(@PathVariable String emotionTypeId) {
        return memoryOrbService.getMemoryOrbsByEmotionTypeId(emotionTypeId);
    }

    /**
     * 특정 ID의 MemoryOrb를 삭제한다.
     * @param id 삭제할 MemoryOrb의 ID
     */
    @DeleteMapping("/{id}")
    public void deleteMemoryOrb(@PathVariable String id) {
        memoryOrbService.deleteMemoryOrb(id);
    }
}