package com.surveymate.api.domain.auth.repository;

import com.surveymate.api.domain.auth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    @Query("SELECT lh FROM LoginHistory lh WHERE lh.uuid = :uuid AND lh.lastAccessTime > :cutoffTime")
    Optional<LoginHistory> findByUuid(@Param("uuid") UUID uuid, @Param("cutoffTime") LocalDateTime cutoffTime);

    @Modifying
    @Transactional
    @Query("UPDATE LoginHistory lh SET lh.lastAccessTime = CURRENT_TIMESTAMP WHERE lh.uuid = :uuid")
    int updateLastAccessTime(UUID uuid);
}
