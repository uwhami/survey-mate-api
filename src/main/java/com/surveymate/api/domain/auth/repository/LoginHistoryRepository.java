package com.surveymate.api.domain.auth.repository;

import com.surveymate.api.domain.auth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    Optional<LoginHistory> findByUuid(UUID uuid);

}
