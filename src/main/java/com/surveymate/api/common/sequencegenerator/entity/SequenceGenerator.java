package com.surveymate.api.common.sequencegenerator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sequence_generator")
public class SequenceGenerator {


    @EmbeddedId
    private SequenceGeneratorId id;

    @Column(name = "current_sequence_number", nullable = false)
    private Integer currentSequenceNumber;


}
