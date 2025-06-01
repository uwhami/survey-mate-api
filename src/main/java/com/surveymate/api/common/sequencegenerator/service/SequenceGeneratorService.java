package com.surveymate.api.common.sequencegenerator.service;

import java.time.LocalDate;

public interface SequenceGeneratorService {

    int getNextSequence(String sequenceType, LocalDate sequenceDate);
}
