package com.example.supplement.dto;

import com.example.supplement.entity.PrescriptionStatus;

import java.time.LocalDate;
import java.util.UUID;

public class PrescriptionResponseDto {
    private final UUID id;
    private final UUID patientId;
    private final String patientName;
    private final UUID supplementId;
    private final String supplementName;
    private final LocalDate date;
    private final PrescriptionStatus status;
    private final String notes;

    public PrescriptionResponseDto(
        UUID id,
        UUID patientId,
        String patientName,
        UUID supplementId,
        String supplementName,
        LocalDate date,
        PrescriptionStatus status,
        String notes
    ) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.supplementId = supplementId;
        this.supplementName = supplementName;
        this.date = date;
        this.status = status;
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public UUID getSupplementId() {
        return supplementId;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public LocalDate getDate() {
        return date;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}
