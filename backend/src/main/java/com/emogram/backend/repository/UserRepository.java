package com.emogram.backend.repository;

import com.emogram.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
    이메일로 사용자 조회
     */
    @Query("SELECT u FROM User u WHERE u.userInfo.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
