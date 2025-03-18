package com.emogram.backend.controller;

import com.emogram.backend.entity.MemoryOrb;
import com.emogram.backend.service.MemoryOrbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/memory-orbs")
@RequiredArgsConstructor
public class MemoryOrbController {

    private final MemoryOrbService memoryOrbService;

    @PostMapping
    public ResponseEntity<MemoryOrb> saveMemoryOrb(@RequestParam String userId,
                                                   @RequestParam String emotionType,
                                                   @RequestParam String description){
        MemoryOrb savedMemoryOrb = memoryOrbService.saveMemoryOrb(userId, emotionType, description);

        return ResponseEntity.ok(savedMemoryOrb);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MemoryOrb>> getUserMemoryOrbs(@PathVariable String userId){
        return ResponseEntity.ok(memoryOrbService.getUserMemoryOrbs(userId));
    }

    @GetMapping("/{userId}/range")
    public ResponseEntity<List<MemoryOrb>> getUserMemoryOrbs(@PathVariable String userId,
                                                             @PathVariable LocalDateTime start,
                                                             @PathVariable LocalDateTime end){
        return ResponseEntity.ok(memoryOrbService.getUserMemoryOrbsByDateRange(userId, start, end));
    }
}
