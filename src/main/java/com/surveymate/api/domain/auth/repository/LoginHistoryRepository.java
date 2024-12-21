package com.surveymate.api.domain.auth.repository;

import com.surveymate.api.domain.auth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    Optional<LoginHistory> findByUuid(@Param("uuid") UUID uuid);

}
