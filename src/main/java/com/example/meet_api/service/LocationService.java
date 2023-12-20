package com.example.meet_api.service;

import com.example.meet_api.domain.Location;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    // 초대한 사람이 맵을 생성했을 때
    @Transactional
    public Long addLocation(LocationCreateDto dto){

        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,
                currentLocale);

        String nowDate = formatter.format(today);

        String chatRoomId = "Chat_"+dto.getMyLoginId()+"_"+dto.getOtherLoginId()+"_"+nowDate;

        Location newLocation = Location.builder()
                .ownerId(dto.getMyLoginId())
                .otherId(dto.getOtherLoginId())
                .ownerLatitude(dto.getOwnerLatitude())
                .ownerLongitude(dto.getOwnerLongitude())
                .ownerAddress(dto.getOwnerAddress())
                .destinationLatitude(dto.getDestinationLatitude())
                .destinationLongitude(dto.getDestinationLongitude())
                .destinationAddress(dto.getDestinationAddress())
                .chatRoomId(chatRoomId)
                .status("W")
                .build();

        Location location = locationRepository.save(newLocation);

        return location.getId();
    }

    // 초대 받은 사람이 수락했을 때
    @Transactional
    public void acceptInvite(LocationCreateDto dto) {

        Location location = locationRepository.findById(dto.getLocationId()).orElseThrow(NullPointerException::new);

        // 상대방 경도 위도, 상태 업데이트
        location.setOtherLatitude(dto.getOtherLatitude());
        location.setOtherLongitude(dto.getOtherLongitude());
        location.setOtherAddress(dto.getOtherAddress());
        location.setStatus("A");
    }
}
