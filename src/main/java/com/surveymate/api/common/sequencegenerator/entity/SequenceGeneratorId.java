package com.surveymate.api.common.sequencegenerator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SequenceGeneratorId implements Serializable {

    @Column(name = "sequence_type", length = 50, nullable = false)
    private String sequenceType;

    @Column(name = "sequence_date", nullable = false)
    private LocalDate sequenceDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequenceGeneratorId that = (SequenceGeneratorId) o;
        return Objects.equals(sequenceType, that.sequenceType) &&
                Objects.equals(sequenceDate, that.sequenceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceType, sequenceDate);
    }

}
