package com.surveymate.api.domain.auth.repository;

import com.surveymate.api.domain.auth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    LoginHistory findByUuid(@Param("uuid") byte[] uuid);

}
