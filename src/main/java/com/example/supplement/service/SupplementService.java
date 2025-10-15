package com.example.supplement.service;

import com.example.supplement.dto.SupplementRequestDto;
import com.example.supplement.dto.SupplementResponseDto;
import com.example.supplement.entity.Supplement;
import com.example.supplement.mapper.SupplementMapper;
import com.example.supplement.repository.SupplementRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SupplementService {
    private final SupplementRepository repository;
    private final SupplementMapper mapper;

    public SupplementService(SupplementRepository repository, SupplementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public SupplementResponseDto createSupplement(SupplementRequestDto dto) {
        Supplement supplement = mapper.toEntity(dto);
        repository.save(supplement);
        return mapper.toResponseDto(supplement);
    }

    public List<SupplementResponseDto> listSupplements() {
        return repository.findAll().stream()
            .map(mapper::toResponseDto)
            .collect(Collectors.toList());
    }

    public Optional<Supplement> findById(UUID id) {
        return repository.findById(id);
    }
}
