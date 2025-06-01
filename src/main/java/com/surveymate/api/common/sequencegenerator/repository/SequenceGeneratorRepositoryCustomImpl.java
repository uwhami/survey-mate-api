package com.surveymate.api.common.sequencegenerator.repository;


import com.surveymate.api.common.sequencegenerator.entity.SequenceGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceGeneratorRepositoryCustomImpl implements SequenceGeneratorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SequenceGenerator persistNew(SequenceGenerator sequenceGenerator) {
        entityManager.persist(sequenceGenerator);
        entityManager.flush();
        return sequenceGenerator;
    }
}
