package com.surveymate.api.common.sequencegenerator.service;

import com.surveymate.api.common.sequencegenerator.entity.SequenceGenerator;
import com.surveymate.api.common.sequencegenerator.entity.SequenceGeneratorId;
import com.surveymate.api.common.sequencegenerator.exception.SequenceRetrievalFailureException;
import com.surveymate.api.common.sequencegenerator.repository.SequenceGeneratorRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    private final SequenceGeneratorRepository sequenceGeneratorRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int getNextSequence(String sequenceType, LocalDate sequenceDate) {
        SequenceGeneratorId id = new SequenceGeneratorId(sequenceType, sequenceDate);
        int updatedRows = sequenceGeneratorRepository.incrementSequenceNumber(sequenceType, sequenceDate);

        if (updatedRows > 0) {
            SequenceGenerator sequenceGenerator = sequenceGeneratorRepository.findById(id).orElseThrow(() -> new SequenceRetrievalFailureException(
                    "Sequence was updated on retry but could not be retrieved."));
            return sequenceGenerator.getCurrentSequenceNumber();
        } else {
            try {
                SequenceGenerator sequenceGenerator = createNewSequenceWithInitialValue(id);
                return sequenceGenerator.getCurrentSequenceNumber();
            } catch (EntityExistsException | DataIntegrityViolationException e) {

                int retryUpdatedRows = sequenceGeneratorRepository.incrementSequenceNumber(sequenceType, sequenceDate);
                if (retryUpdatedRows > 0) {
                    SequenceGenerator sequenceGeneratorOnRetry = sequenceGeneratorRepository.findById(id)
                            .orElseThrow(() -> new SequenceRetrievalFailureException(
                                    "Sequence was updated on retry but could not be retrieved."));
                    return sequenceGeneratorOnRetry.getCurrentSequenceNumber();
                } else {
                    throw new SequenceRetrievalFailureException(
                            "Failed to obtain sequence after creation conflict and retry.");
                }
            }
        }

    }


    protected SequenceGenerator createNewSequenceWithInitialValue(SequenceGeneratorId id) {
        SequenceGenerator newSequence = new SequenceGenerator();
        newSequence.setId(id);
        newSequence.setCurrentSequenceNumber(1);
        return sequenceGeneratorRepository.persistNew(newSequence);
    }
}
