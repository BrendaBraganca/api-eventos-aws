package com.eventostec.api.domain.cupon;

public record CuponRequestDTO(String code, Integer discount, Long valid) {
    
}
