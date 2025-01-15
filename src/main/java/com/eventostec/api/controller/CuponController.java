package com.eventostec.api.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventostec.api.domain.cupon.Cupon;
import com.eventostec.api.domain.cupon.CuponRequestDTO;
import com.eventostec.api.services.CuponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupon")
@RequiredArgsConstructor
public class CuponController {

    private final CuponService cuponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Cupon> addCupponsToEvent(@PathVariable UUID eventId, @RequestBody CuponRequestDTO data){
        Cupon cupons = cuponService.addCupponToEvent(eventId, data);
        return ResponseEntity.ok(cupons);

    }
}
