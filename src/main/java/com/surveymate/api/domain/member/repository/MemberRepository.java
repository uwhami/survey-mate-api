package com.surveymate.api.domain.member.repository;

import com.surveymate.api.common.enums.MemberStatus;
import com.surveymate.api.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemNum(String memNum);

    Optional<Member> findByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM Member m WHERE m.userId = :userId AND m.memStatus = :status")
    Optional<Member> findByUserId(@Param("userId") String userId, @Param("status") MemberStatus status);

    Optional<Member> findByUserEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.userEmail = :email AND m.memStatus = :status")
    Optional<Member> findByUserEmail(@Param("email") String email, @Param("status") MemberStatus status);

    @Query("SELECT NVL(MAX(CAST(SUBSTRING(m.memNum, 10) AS integer)), 0) FROM Member m WHERE m.memNum LIKE CONCAT('M', :today, '%') ORDER BY m.memNum DESC LIMIT 1")
    int findTopMemNumByToday(@Param("today") String today);

    @Modifying
    @Query("UPDATE Member m SET m.passwordError = m.passwordError + 1 WHERE m.userId = :userId")
    void increasePasswordError(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE Member m SET m.passwordError = 0 WHERE m.userId = :userId")
    void resetPasswordError(@Param("userId") String userId);

    int findPasswordErrorByUserId(String userId);

    String findUserIdByUserEmail(String email);

}
