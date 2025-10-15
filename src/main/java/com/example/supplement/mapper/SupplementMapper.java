package com.example.supplement.mapper;

import com.example.supplement.dto.SupplementRequestDto;
import com.example.supplement.dto.SupplementResponseDto;
import com.example.supplement.entity.Supplement;

public class SupplementMapper {

    public Supplement toEntity(SupplementRequestDto dto) {
        return new Supplement(dto.getName(), dto.getActiveIngredients());
    }

    public SupplementResponseDto toResponseDto(Supplement supplement) {
        return new SupplementResponseDto(
            supplement.getId(),
            supplement.getName(),
            supplement.getActiveIngredients()
        );
    }
}
