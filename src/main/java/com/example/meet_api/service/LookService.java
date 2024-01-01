package com.example.meet_api.service;

import com.example.meet_api.domain.Location.Location;
import com.example.meet_api.domain.Look;
import com.example.meet_api.dto.Location.LookCreateDto;
import com.example.meet_api.repository.LocationRepository;
import com.example.meet_api.repository.LookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LookService {

    private final LookRepository lookRepository;

    private final LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public Look getLookByLocationIdAndLoginId(Long LocationId, String loginId){
        return lookRepository.getLookByLocationIdAndLoginId(LocationId, loginId).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void updateLook(LookCreateDto look){
        lookRepository.updateLook(look.getId(), look.getHat(), look.getTop(), look.getBottom(), look.getShoes(), look.getEtc(), look.getOuter());

        Location location = locationRepository.findById(look.getLocationId()).orElseThrow(NullPointerException::new);
        location.setDestinationDetailAddress(look.getDestinationDetailAddress());
    }

}
