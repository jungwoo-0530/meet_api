package com.example.meet_api.controller;

import com.example.meet_api.domain.InviteInfo;
import com.example.meet_api.domain.Location.Location;
import com.example.meet_api.domain.Location.LocationDetail;
import com.example.meet_api.domain.Look;
import com.example.meet_api.domain.Member;
import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.dto.InviteInfo.InviteInfoDto;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.dto.Location.LocationResponseDto;
import com.example.meet_api.service.LocationService;
import com.example.meet_api.service.LookService;
import com.example.meet_api.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@Slf4j
@RequiredArgsConstructor
public class MapRestController {

    private final LocationService locationService;

    private final MemberService memberService;

    private final LookService lookService;


    /*
     * 리스트
     * */
    @GetMapping("/list")
    public ResponseEntity<? extends BaseResponse> list(@RequestParam("id") String ownerId  ,HttpServletRequest request) {

        List<Location> locationList = locationService.getLocationList(ownerId);

        if(locationList.isEmpty()){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "데이터가 없습니다.", "204"));
        }

        return ResponseEntity.ok().body(new CommonResponse<>(locationList, "목록을 불러왔습니다.", "200"));
    }

    /*
    * 단건 조회
    * */
    @GetMapping("/detail")
    public ResponseEntity<? extends BaseResponse> getOne(@RequestParam("locationId") Long id, @RequestParam("loginId") String loginId, HttpServletRequest request) {

        try{
            Location location = locationService.getLocation(id);

            String otherLoginId = location.getOwnerId().equals(loginId) ? location.getOtherId() : location.getOwnerId();

            LocationDetail myLocationDetail = locationService.getLocationDetail(id, loginId);
            LocationDetail otherLocationDetail = locationService.getLocationDetail(id, otherLoginId);

            Look myLook = lookService.getLookByLocationIdAndLoginId(id, loginId);



            Look otherLook = lookService.getLookByLocationIdAndLoginId(id, otherLoginId);

            LocationResponseDto dto = LocationResponseDto.builder()
                    .id(location.getId())
                    .ownerId(location.getOwnerId())
                    .otherId(location.getOtherId())
                    .ownerLatitude(location.getOwnerLatitude())
                    .ownerLongitude(location.getOwnerLongitude())
                    .ownerAddress(location.getOwnerAddress())
                    .otherLatitude(location.getOtherLatitude())
                    .otherLongitude(location.getOtherLongitude())
                    .otherAddress(location.getOtherAddress())
                    .destinationLatitude(location.getDestinationLatitude())
                    .destinationLongitude(location.getDestinationLongitude())
                    .destinationAddress(location.getDestinationAddress())
                    .destinationDetailAddress(location.getDestinationDetailAddress())
                    .chatRoomId(location.getChatRoomId())
                    .status(location.getStatus())
                    .useYn(location.getUseYn())
                    .currentMyLatitude(myLocationDetail.getLatitude())
                    .currentMyLongitude(myLocationDetail.getLongitude())
                    .currentOtherLatitude(otherLocationDetail.getLatitude())
                    .currentOtherLongitude(otherLocationDetail.getLongitude())
                    .build();

            dto.setMyLook(myLook);
            dto.setOtherLook(otherLook);

            return ResponseEntity.ok().body(new CommonResponse<>(dto, "데이터를 불러왔습니다.", "200"));
        } catch (Exception e){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "데이터가 없습니다.", "204"));
        }

    }

    /*
    * 방 만들기
    * */
    @PostMapping("/add")
    public ResponseEntity<? extends BaseResponse> createLocation(@RequestBody LocationCreateDto dto,  HttpServletRequest request) {

        Member member = memberService.getMemberByLoginId(dto.getOtherLoginId());

        if(member == null){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "존재하지 않는 아이디입니다.", "204"));
        }

        if(locationService.existLocation(dto)){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "해당 유저와 이미 거래 중이거나 대기 중 상태입니다.", "204"));
        }

        Location newLocation = locationService.addLocation(dto);

        return ResponseEntity.ok().body(new CommonResponse<>(newLocation, "등록되었습니다.", "200"));
    }

    /*
    * 초대 수락
    * */
    @PostMapping("/accept")
    public ResponseEntity<? extends BaseResponse> accept(@RequestBody InviteInfoDto dto, HttpServletRequest request) {

        locationService.acceptInvite(dto);

        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }

    /*
    * 초대 목록
    * */
    @GetMapping("/invite/list")
    public ResponseEntity<? extends BaseResponse> inviteList(@RequestParam("id") String inviteeId, HttpServletRequest request) {

        List<InviteInfoDto> inviteList = locationService.getInviteList(inviteeId);


        return ResponseEntity.ok().body(new CommonResponse<>(inviteList, "목록을 불러왔습니다.", "200"));
    }


    @PostMapping("/delete")
    public ResponseEntity<? extends BaseResponse> delete(@RequestBody String body, HttpServletRequest request) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        LocationCreateDto locationCreateDto = objectMapper.readValue(body, LocationCreateDto.class);

        locationService.deleteLocationAndChat(locationCreateDto.getLocationId());

        return ResponseEntity.ok().body(new CommonResponse<>("", "삭제되었습니다.", "200"));
    }

    /*
    * 위치 업데이트
    */
    @PostMapping("/location/update")
    public ResponseEntity<? extends BaseResponse> updateLocation(@RequestBody LocationCreateDto dto, HttpServletRequest request) {

        locationService.updateLocation(dto);

        return ResponseEntity.ok().body(new CommonResponse<>("", "업데이트 되었습니다.", "200"));
    }

    /*
    * 상대방 위치 가져오기
    * */
    @GetMapping("/location/other")
    public ResponseEntity<? extends BaseResponse> getOtherLocation(@RequestParam("locationId")Long id, @RequestParam("otherLoginId") String loginId, HttpServletRequest request) {

        try{
            LocationDetail locationDetail = locationService.getLocationDetail(id, loginId);

            return ResponseEntity.ok().body(new CommonResponse<>(locationDetail, "데이터를 불러왔습니다.", "200"));
        } catch (Exception e){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "데이터가 없습니다.", "204"));
        }
    }


}