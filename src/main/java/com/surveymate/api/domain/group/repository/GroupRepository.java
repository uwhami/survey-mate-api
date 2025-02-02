package com.surveymate.api.domain.group.repository;

import com.surveymate.api.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g FROM Group g WHERE g.groupCode = :groupCode")
    Optional<Group> findByGroupCode(@Param("groupCode") String groupCode);

    @Query("SELECT g FROM Group g WHERE g.groupCode = :groupCode AND g.groupAuthCode = :groupAuthCode")
    Optional<Group> findByGroupCodeAndGroupAuthCode(@Param("groupCode") String groupCode,
                                                    @Param("groupAuthCode") String groupAuthCode);
}
