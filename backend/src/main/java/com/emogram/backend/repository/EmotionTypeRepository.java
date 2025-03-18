package com.emogram.backend.repository;

import com.emogram.backend.entity.EmotionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionTypeRepository extends JpaRepository<EmotionType, Long> {

    Optional<EmotionType> findByType(String type);

}
