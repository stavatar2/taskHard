package com.example.supplement.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Prescription {
    private final UUID id;
    private final Patient patient;
    private final Supplement supplement;
    private final LocalDate date;
    private PrescriptionStatus status;
    private String notes;

    public Prescription(Patient patient, Supplement supplement, LocalDate date, PrescriptionStatus status, String notes) {
        this(UUID.randomUUID(), patient, supplement, date, status, notes);
    }

    public Prescription(UUID id, Patient patient, Supplement supplement, LocalDate date, PrescriptionStatus status, String notes) {
        this.id = Objects.requireNonNull(id, "id");
        this.patient = Objects.requireNonNull(patient, "patient");
        this.supplement = Objects.requireNonNull(supplement, "supplement");
        this.date = Objects.requireNonNull(date, "date");
        this.status = Objects.requireNonNull(status, "status");
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Supplement getSupplement() {
        return supplement;
    }

    public LocalDate getDate() {
        return date;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = Objects.requireNonNull(status, "status");
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
