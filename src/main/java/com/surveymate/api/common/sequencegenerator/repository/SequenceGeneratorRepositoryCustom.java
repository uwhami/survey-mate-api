package com.surveymate.api.common.sequencegenerator.repository;

import com.surveymate.api.common.sequencegenerator.entity.SequenceGenerator;

public interface SequenceGeneratorRepositoryCustom {
    SequenceGenerator persistNew(SequenceGenerator sequenceGenerator);
}
