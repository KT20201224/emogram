package com.emogram.backend.entity;

import jakarta.persistence.*;
import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArraysOfInt;

/*
    감정에 따른 색상을 정리하는 엔티티
    EX) type : 기쁨, color : #FFD700
 */

@Entity
@Table(name = "emotion_types")
public class EmotionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @Column(nullable = false, unique = true)
    private String color;

    public EmotionType(String type, String color){
        this.type = type;
        this.color = color;
    }
}
