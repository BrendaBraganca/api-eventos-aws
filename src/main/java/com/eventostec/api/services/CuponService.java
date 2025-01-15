package com.eventostec.api.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventostec.api.domain.cupon.Cupon;
import com.eventostec.api.domain.cupon.CuponRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.repositories.CuponRepository;
import com.eventostec.api.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuponService {

    private final CuponRepository cuponRepository;
    private final EventRepository eventRepository;

    public Cupon addCupponToEvent(UUID eventId, CuponRequestDTO cuponRequestDTO){
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));
        Cupon cupon = new Cupon();
        cupon.setCode(cuponRequestDTO.code());
        cupon.setDiscount(cuponRequestDTO.discount());
        cupon.setValid(new Date(cuponRequestDTO.valid()));
        cupon.setEvent(event);

        return cuponRepository.save(cupon);
    }

    public List<Cupon> consultCupons(UUID eventId, Date currentDate){
        return cuponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
    public List<Cupon> consultCoupons(UUID eventId, Date currentDate) {
        return cuponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
}
