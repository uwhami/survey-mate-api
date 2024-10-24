package com.surveymate.api.domain.member.repository;

import com.surveymate.api.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {



//    @Query("SELECT m.memNum FROM Member m WHERE m.memNum LIKE CONCAT('M', :today, '%') ORDER BY m.memNum DESC")
    @Query("SELECT m.memNum FROM Member m WHERE m.memNum LIKE CONCAT('M', :today, '%') ORDER BY m.memNum DESC LIMIT 1")
    String findTopMemNumByToday(@Param("today") String today);

}
