package com.example.supplement.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class PrescriptionRequestDto {
    private final UUID patientId;
    private final UUID supplementId;
    private final LocalDate date;

    public PrescriptionRequestDto(UUID patientId, UUID supplementId, LocalDate date) {
        this.patientId = Objects.requireNonNull(patientId, "patientId");
        this.supplementId = Objects.requireNonNull(supplementId, "supplementId");
        this.date = Objects.requireNonNull(date, "date");
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getSupplementId() {
        return supplementId;
    }

    public LocalDate getDate() {
        return date;
    }
}
