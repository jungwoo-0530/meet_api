package com.example.meet_api.service;

import com.example.meet_api.domain.Chat;
import com.example.meet_api.domain.InviteInfo;
import com.example.meet_api.domain.Location.Location;
import com.example.meet_api.domain.Location.LocationDetail;
import com.example.meet_api.domain.Look;
import com.example.meet_api.dto.Chat.ChatCreateDto;
import com.example.meet_api.dto.InviteInfo.InviteInfoDto;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private final LocationDetailRepository locationDetailRepository;

    private final InviteInfoRepository inviteInfoRepository;

    private final ChatService chatService;

    private final ChatRepository chatRepository;

    private final LookRepository lookRepository;

    // 초대한 사람이 맵을 생성했을 때
    @Transactional
    public Location addLocation(LocationCreateDto dto){

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
                .destinationDetailAddress(dto.getDestinationDetailAddress())
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
                .chatRoomId(chatRoomId)
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


        // 인상착의
        Look look = Look.builder()
                .locationId(location.getId())
                .loginId(dto.getMyLoginId())
                .hat(dto.getHat())
                .top(dto.getTop())
                .bottom(dto.getBottom())
                .shoes(dto.getShoes())
                .etc(dto.getEtc())
                .outerCloth(dto.getOuter())
                .build();

        lookRepository.save(look);

        return location;

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

            // 인상착의
            Look look = Look.builder()
                    .locationId(location.getId())
                    .loginId(dto.getInviteeId())
                    .hat(dto.getHat())
                    .top(dto.getTop())
                    .bottom(dto.getBottom())
                    .shoes(dto.getShoes())
                    .etc(dto.getEtc())
                    .outerCloth(dto.getOuter())
                    .build();

            lookRepository.save(look);

        } else{
            // 거절
            location.setStatus("C");

            chatService.updateChatStatus(location.getId(), "C");
        }
    }

    @Transactional(readOnly = true)
    public List<InviteInfoDto> getInviteList(String inviteeId) {

        List<InviteInfoDto> inviteInfoDtoList = new ArrayList<>();

        List<InviteInfo> allByInviteeId = inviteInfoRepository.findAllByInviteeId(inviteeId);

        for (InviteInfo inviteInfo : allByInviteeId) {
            Location location = locationRepository.findById(inviteInfo.getLocationId()).orElseThrow(NullPointerException::new);

            InviteInfoDto inviteInfoDto = InviteInfoDto.builder()
                    .id(inviteInfo.getId())
                    .locationId(inviteInfo.getLocationId())
                    .inviterId(inviteInfo.getInviterId())
                    .inviteeId(inviteInfo.getInviteeId())
                    .chatRoomId(inviteInfo.getChatRoomId())
                    .inviterAddress(location.getOwnerAddress())
                    .destinationAddress(location.getDestinationAddress())
                    .destinationDetailAddress(location.getDestinationDetailAddress())
                    .build();

            inviteInfoDtoList.add(inviteInfoDto);
        }

        return inviteInfoDtoList;
    }

    @Transactional(readOnly = true)
    public List<Location> getLocationList(String loginId) {

        return locationRepository.findAllByOwnerId(loginId);
    }

    @Transactional
    public void deleteLocationAndChat(Long locationId){

        Location location = locationRepository.findById(locationId).orElseThrow(NullPointerException::new);

        location.setUseYn("N");
        location.setStatus("E");

        Chat chat = chatRepository.findByLocationId(locationId).orElseThrow(NullPointerException::new);
        chat.setStatus("E");
        chat.setUseYn("N");

        locationDetailRepository.deleteLocationDetailByLocationId(locationId);

        lookRepository.deleteLookByLocationId(locationId);
    }

    @Transactional
    public void updateLocation(LocationCreateDto dto) {

        // 현재 실행 중인 위치 서비스를 전체 업데이트 해야함.
        locationDetailRepository.updateLocationDetail(dto.getMyLoginId(), dto.getOwnerLatitude(), dto.getOwnerLongitude());

    }

    @Transactional(readOnly = true)
    public LocationDetail getLocationDetail(Long locationId, String loginId){

        return locationDetailRepository.getLocationDetailByLocationIdAndLoginId(locationId, loginId).orElseThrow();

    }

    @Transactional(readOnly = true)
    public Location getLocation(Long locationId){

        return locationRepository.findById(locationId).orElseThrow();

    }

    public boolean existLocation(LocationCreateDto dto) {
        return locationRepository.findLocationByOwnerIdAndOtherId(dto.getMyLoginId(), dto.getOtherLoginId()) > 0;
    }
}