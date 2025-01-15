package com.eventostec.api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.repositories.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    
    public void createAddress(EventRequestDTO eventRequestDTO, Event event){
        Address address = new Address();
        address.setCity(eventRequestDTO.city());
        address.setUf(eventRequestDTO.state());
        address.setEnvent(event);
        addressRepository.save(address);
    }

    public Optional <Address> findByEventId(UUID event_id){
        return addressRepository.findByEventId(event_id);
    }
}
