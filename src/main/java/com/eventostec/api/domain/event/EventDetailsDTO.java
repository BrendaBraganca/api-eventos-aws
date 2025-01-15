package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(        UUID id,
        String title,
        String description,
        Date date,
        String city,
        String state,
        String imgUrl,
        String eventUrl,
        List<CuponDTO> coupons){ public record CuponDTO(
            String code,
            Integer discount,
            Date valid) {}
        }



