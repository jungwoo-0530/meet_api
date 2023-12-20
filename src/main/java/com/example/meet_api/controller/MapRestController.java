package com.example.meet_api.controller;

import com.example.meet_api.domain.Member;
import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.service.LocationService;
import com.example.meet_api.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map")
@Slf4j
@RequiredArgsConstructor
public class MapRestController {

    private final LocationService locationService;

    private final MemberService memberService;


    /*
     * 리스트
     * */
    @GetMapping("/list")
    public ResponseEntity<? extends BaseResponse> list(HttpServletRequest request) {
        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }

    /*
    * 방 만들기
    * */
    @PostMapping("/add")
    public ResponseEntity<? extends BaseResponse> createLocation(@RequestBody LocationCreateDto dto,  HttpServletRequest request) {

        Member member = memberService.getMemberByLoginId(dto.getOtherLoginId());

        if(member == null){
            return ResponseEntity.status(400).body(new CommonResponse<>("", "존재하지 않는 아이디입니다.", "400"));
        }

        locationService.addLocation(dto);

        return ResponseEntity.ok().body(new CommonResponse<>("", "등록되었습니다.", "200"));
    }

    /*
    * 초대 수락
    * */
    @PostMapping("/accept")
    public ResponseEntity<? extends BaseResponse> accept(@RequestBody LocationCreateDto dto, HttpServletRequest request) {

        locationService.acceptInvite(dto);

        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }


}