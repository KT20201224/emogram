package com.emogram.backend.controller;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.service.MemoryOrbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * MemoryOrbController - MemoryOrb 관련 API를 처리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/memory-orbs")
public class MemoryOrbController {

    private final MemoryOrbService memoryOrbService;

    @Autowired
    public MemoryOrbController(MemoryOrbService memoryOrbService) {
        this.memoryOrbService = memoryOrbService;
    }

    @PostMapping
    public MemoryOrb createMemoryOrb(@RequestBody MemoryOrb memoryOrb) {
        return memoryOrbService.saveMemoryOrb(memoryOrb);
    }

    @GetMapping("/{id}")
    public Optional<MemoryOrb> getMemoryOrbById(@PathVariable String id) {
        return memoryOrbService.getMemoryOrbById(id);
    }

    @GetMapping
    public List<MemoryOrb> getAllMemoryOrbs() {
        return memoryOrbService.getAllMemoryOrbs();
    }

    @GetMapping("/emotion/{emotionTypeId}")
    public List<MemoryOrb> getMemoryOrbsByEmotionTypeId(@PathVariable String emotionTypeId) {
        return memoryOrbService.getMemoryOrbsByEmotionTypeId(emotionTypeId);
    }

    @DeleteMapping("/{id}")
    public void deleteMemoryOrb(@PathVariable String id) {
        memoryOrbService.deleteMemoryOrb(id);
    }
}