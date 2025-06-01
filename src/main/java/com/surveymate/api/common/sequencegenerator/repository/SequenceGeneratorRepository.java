package com.surveymate.api.common.sequencegenerator.repository;

import com.surveymate.api.common.sequencegenerator.entity.SequenceGenerator;
import com.surveymate.api.common.sequencegenerator.entity.SequenceGeneratorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SequenceGeneratorRepository extends JpaRepository<SequenceGenerator, SequenceGeneratorId>, SequenceGeneratorRepositoryCustom {
    /**
     * @param sequenceType 시퀀스 타입
     * @param sequenceDate 시퀀스 날짜
     * @return 업데이트된 행의 수
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true) // 영속성 컨텍스트 동기화 및 DB 반영 옵션
    @Query("UPDATE SequenceGenerator sg SET sg.currentSequenceNumber = sg.currentSequenceNumber + 1 " +
            "WHERE sg.id.sequenceType = :sequenceType AND sg.id.sequenceDate = :sequenceDate")
    int incrementSequenceNumber(@Param("sequenceType") String sequenceType, @Param("sequenceDate") LocalDate sequenceDate);

    Optional<SequenceGenerator> findById(SequenceGeneratorId id);

}
