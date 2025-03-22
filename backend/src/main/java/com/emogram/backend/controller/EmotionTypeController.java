package com.emogram.backend.controller;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.service.EmotionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotion-types")
public class EmotionTypeController {

    private final EmotionTypeService emotionTypeService;

    @Autowired
    public EmotionTypeController(EmotionTypeService emotionTypeService) {
        this.emotionTypeService = emotionTypeService;
    }

    @PostMapping
    public ResponseEntity<EmotionTypeDto> createEmotionType(@RequestBody EmotionTypeDto dto) {
        EmotionTypeDto createdDto = emotionTypeService.createEmotionType(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmotionTypeDto>> getAllEmotionTypes() {
        List<EmotionTypeDto> emotionTypes = emotionTypeService.getAllEmotionTypes();
        return new ResponseEntity<>(emotionTypes, HttpStatus.OK);
    }
}