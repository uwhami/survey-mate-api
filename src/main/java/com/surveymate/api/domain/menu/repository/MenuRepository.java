package com.surveymate.api.domain.menu.repository;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {

    // 특정 접두사로 시작하는 메뉴 번호 중 가장 큰 번호 조회
    @Query("SELECT MAX(m.menuNo) FROM Menu m WHERE m.menuNo LIKE 'MN%' AND m.parentMenuNo IS NULL")
    String findMaxMenuNoByPrefix(@Param("prefix") String prefix);

    // 특정 부모 메뉴의 하위 메뉴 중 가장 큰 메뉴 번호 조회
    @Query("SELECT MAX(m.menuNo) FROM Menu m WHERE m.parentMenuNo = :parentMenuNo")
    String findMaxMenuNoByParentMenuNo(@Param("parentMenuNo") String parentMenuNo);

    // 관리자가 사용자 권한기준으로 사용중인 메뉴 조회 (메뉴 레이아웃 조회)
    @Query("SELECT m FROM Menu m WHERE m.memRole = :role AND m.useYn = 'Y' ORDER BY m.menuNo,m.sequence")
    List<Menu> findByMemRole(@Param("role") MemberRole role);

    // 모든 메뉴 조회
    @Query("SELECT m FROM Menu m ORDER BY m.menuNo,m.sequence")
    List<Menu> findAllMenus();
}
