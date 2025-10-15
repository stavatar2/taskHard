package com.example.supplement.service;

import com.example.supplement.dto.PrescriptionRequestDto;
import com.example.supplement.dto.PrescriptionResponseDto;
import com.example.supplement.entity.Disease;
import com.example.supplement.entity.Patient;
import com.example.supplement.entity.Prescription;
import com.example.supplement.entity.PrescriptionStatus;
import com.example.supplement.entity.Supplement;
import com.example.supplement.mapper.PrescriptionMapper;
import com.example.supplement.repository.PrescriptionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PatientService patientService;
    private final SupplementService supplementService;
    private final DiseaseService diseaseService;
    private final PrescriptionMapper prescriptionMapper;

    public PrescriptionService(
        PrescriptionRepository prescriptionRepository,
        PatientService patientService,
        SupplementService supplementService,
        DiseaseService diseaseService,
        PrescriptionMapper prescriptionMapper
    ) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientService = patientService;
        this.supplementService = supplementService;
        this.diseaseService = diseaseService;
        this.prescriptionMapper = prescriptionMapper;
    }

    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto requestDto) {
        Objects.requireNonNull(requestDto, "requestDto");

        Patient patient = patientService.findById(requestDto.getPatientId())
            .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + requestDto.getPatientId()));

        Supplement supplement = supplementService.findById(requestDto.getSupplementId())
            .orElseThrow(() -> new IllegalArgumentException("Supplement not found: " + requestDto.getSupplementId()));

        List<Disease> diseases = patient.getDiseases();
        Set<String> conflicts = diseaseService.findConflictingSubstances(supplement, diseases);

        Prescription prescription = new Prescription(patient, supplement, requestDto.getDate(), PrescriptionStatus.PENDING, null);

        if (!conflicts.isEmpty()) {
            prescription.setStatus(PrescriptionStatus.REJECTED);
            prescription.setNotes("Запрещенные вещества: " + String.join(", ", conflicts));
            prescriptionRepository.save(prescription);
            return prescriptionMapper.toResponseDto(prescription);
        }

        ensureNoDuplicateActiveIngredients(patient.getId(), supplement);

        if (violatesAgeConstraint(patient, supplement)) {
            prescription.setStatus(PrescriptionStatus.REJECTED);
            prescription.setNotes("Возрастные ограничения для добавки с множеством веществ");
            prescriptionRepository.save(prescription);
            return prescriptionMapper.toResponseDto(prescription);
        }

        prescription.setStatus(PrescriptionStatus.ACCEPTED);
        prescription.setNotes("Назначение одобрено");
        prescriptionRepository.save(prescription);
        patientService.addAcceptedSupplement(patient.getId(), supplement);
        return prescriptionMapper.toResponseDto(prescription);
    }

    private void ensureNoDuplicateActiveIngredients(UUID patientId, Supplement supplement) {
        Set<String> incoming = new HashSet<>(supplement.getActiveIngredients());
        List<Prescription> existing = prescriptionRepository.findByPatientId(patientId);

        boolean duplicates = existing.stream()
            .filter(prescription -> prescription.getStatus() != PrescriptionStatus.REJECTED)
            .map(Prescription::getSupplement)
            .map(Supplement::getActiveIngredients)
            .flatMap(Collection::stream)
            .anyMatch(incoming::contains);

        if (duplicates) {
            throw new IllegalStateException("Назначение содержит вещества, уже присутствующие в других добавках пациента");
        }
    }

    private boolean violatesAgeConstraint(Patient patient, Supplement supplement) {
        int age = patient.getAge();
        return (age < 12 || age > 75) && supplement.getActiveIngredients().size() > 3;
    }

    public List<PrescriptionResponseDto> listPrescriptions() {
        return prescriptionRepository.findAll().stream()
            .map(prescriptionMapper::toResponseDto)
            .collect(Collectors.toList());
    }
}
