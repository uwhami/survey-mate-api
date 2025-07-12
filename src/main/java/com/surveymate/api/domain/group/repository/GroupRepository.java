package com.surveymate.api.domain.group.repository;

import com.surveymate.api.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupCode(@Param("groupCode") String groupCode);

    Optional<Group> findByGroupCodeAndGroupAuthCode(@Param("groupCode") String groupCode, @Param("groupAuthCode") String groupAuthCode);


}
