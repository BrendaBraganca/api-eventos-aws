package com.eventostec.api.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eventostec.api.domain.cupon.Cupon;

public interface CuponRepository extends JpaRepository<Cupon, UUID>{
    List<Cupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);
}
