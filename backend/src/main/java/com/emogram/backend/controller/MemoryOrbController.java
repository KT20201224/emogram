package com.emogram.backend.controller;

import com.emogram.backend.dto.MemoryOrbRequest;
import com.emogram.backend.dto.MemoryOrbResponse;
import com.emogram.backend.service.MemoryOrbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memory-orbs")
public class MemoryOrbController {

    private final MemoryOrbService memoryOrbService;

    @Autowired
    public MemoryOrbController(MemoryOrbService memoryOrbService) {
        this.memoryOrbService = memoryOrbService;
    }

    @PostMapping
    public ResponseEntity<MemoryOrbResponse> createMemoryOrb(@RequestBody MemoryOrbRequest request) {
        MemoryOrbResponse response = memoryOrbService.createMemoryOrb(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemoryOrbResponse>> getAllMemoryOrbs() {
        return ResponseEntity.ok(memoryOrbService.getAllMemoryOrbs());
    }

    @GetMapping
    public ResponseEntity<List<MemoryOrbResponse>> getMemoryOrbsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(memoryOrbService.getMemoryOrbsByUserId(userId));
    }

    // ✅ 오늘의 기억 구슬만 조회하는 API 추가
    @GetMapping("/user/today")
    public ResponseEntity<List<MemoryOrbResponse>> getTodayMemoryOrbsByUserId(@RequestParam Long userId) {
        List<MemoryOrbResponse> response = memoryOrbService.getTodayMemoryOrbsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orbId}")
    public ResponseEntity<MemoryOrbResponse> updateMemoryOrb(@PathVariable String orbId,
                                                             @RequestBody MemoryOrbRequest request) {
        MemoryOrbResponse response = memoryOrbService.updateMemoryOrb(orbId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orbId}")
    public ResponseEntity<Void> deleteMemoryOrb(@PathVariable String orbId) {
        memoryOrbService.deleteMemoryOrb(orbId);
        return ResponseEntity.noContent().build();
    }
}