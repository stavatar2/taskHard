package com.example.supplement.mapper;

import com.example.supplement.dto.PrescriptionResponseDto;
import com.example.supplement.entity.Prescription;

public class PrescriptionMapper {

    public PrescriptionResponseDto toResponseDto(Prescription prescription) {
        return new PrescriptionResponseDto(
            prescription.getId(),
            prescription.getPatient().getId(),
            prescription.getPatient().getName(),
            prescription.getSupplement().getId(),
            prescription.getSupplement().getName(),
            prescription.getDate(),
            prescription.getStatus(),
            prescription.getNotes()
        );
    }
}
