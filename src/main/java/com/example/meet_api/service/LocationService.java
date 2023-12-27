package com.example.meet_api.service;

import com.example.meet_api.domain.InviteInfo;
import com.example.meet_api.domain.Location.Location;
import com.example.meet_api.domain.Location.LocationDetail;
import com.example.meet_api.dto.Chat.ChatCreateDto;
import com.example.meet_api.dto.InviteInfo.InviteInfoDto;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.repository.ChatRepository;
import com.example.meet_api.repository.InviteInfoRepository;
import com.example.meet_api.repository.LocationDetailRepository;
import com.example.meet_api.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private final LocationDetailRepository locationDetailRepository;

    private final InviteInfoRepository inviteInfoRepository;

    private final ChatService chatService;

    private final ChatRepository chatRepository;

    // 초대한 사람이 맵을 생성했을 때
    @Transactional
    public void addLocation(LocationCreateDto dto){

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
                .useYn("Y")
                .build();

        Location location = locationRepository.save(newLocation);

        // 방 만든 사람 경도, 위도 위치 Detail insert
        LocationDetail locationDetail = LocationDetail.builder()
                .locationId(location.getId())
                .loginId(dto.getMyLoginId())
                .latitude(dto.getOwnerLatitude())
                .longitude(dto.getOwnerLongitude())
                .build();

        locationDetailRepository.save(locationDetail);

        //초대 insert
        InviteInfo inviteInfo = InviteInfo.builder()
                .locationId(location.getId())
                .inviterId(dto.getMyLoginId())
                .inviteeId(dto.getOtherLoginId())
                .build();

        inviteInfoRepository.save(inviteInfo);

        // 채팅방 생성
        ChatCreateDto chatCreateDto = ChatCreateDto.builder()
                .locationId(location.getId())
                .ownerId(dto.getMyLoginId())
                .otherId(dto.getOtherLoginId())
                .status("W")
                .useYn("Y")
                .build();

        chatService.addChat(chatCreateDto);

    }

    // 초대 받은 사람이 수락했을 때
    @Transactional
    public void acceptInvite(InviteInfoDto dto) {

        // 초대 목록에서 제거
        inviteInfoRepository.deleteById(dto.getId());


        Location location = locationRepository.findById(dto.getLocationId()).orElseThrow(NullPointerException::new);

        //수락
        if(Objects.equals(dto.getStatus(), "Y")){
            // 상대방 경도 위도, 상태 업데이트
            location.setOtherLatitude(dto.getInviteeLatitude());
            location.setOtherLongitude(dto.getInviteeLongitude());
            location.setOtherAddress(dto.getInviteeAddress());
            location.setStatus("A");

            // 채팅방 상태 업데이트
            chatService.updateChatStatus(location.getId(), "A");

            // 상대방 경도, 위도 위치 Detail insert
            LocationDetail locationDetail = LocationDetail.builder()
                    .locationId(location.getId())
                    .loginId(dto.getInviteeId())
                    .latitude(dto.getInviteeLatitude())
                    .longitude(dto.getInviteeLongitude())
                    .build();

            locationDetailRepository.save(locationDetail);

        } else{
            // 거절
            location.setStatus("C");

            chatService.updateChatStatus(location.getId(), "C");
        }
    }

    @Transactional(readOnly = true)
    public List<InviteInfo> getInviteList(String inviteeId) {

        return inviteInfoRepository.findAllByInviteeId(inviteeId);
    }

    @Transactional(readOnly = true)
    public List<Location> getLocationList(String loginId) {

        return locationRepository.findAllByOwnerId(loginId);
    }

    @Transactional
    public void deleteLocationAndChat(Long locationId){

        locationRepository.findById(locationId).orElseThrow(NullPointerException::new).setUseYn("N");

        chatRepository.findByLocationId(locationId).orElseThrow(NullPointerException::new).setUseYn("N");
    }

    @Transactional
    public void updateLocation(LocationCreateDto dto) {

        locationDetailRepository.updateLocationDetail(dto.getLocationId(), dto.getMyLoginId(), dto.getOwnerLatitude(), dto.getOwnerLongitude());

    }

    @Transactional(readOnly = true)
    public LocationDetail getLocationDetail(Long locationId, String loginId){

        return locationDetailRepository.getLocationDetailByLocationIdAndLoginId(locationId, loginId).orElseThrow();

    }

    @Transactional(readOnly = true)
    public Location getLocation(Long locationId){

        return locationRepository.findById(locationId).orElseThrow();

    }
}