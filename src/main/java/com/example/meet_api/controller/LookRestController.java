package com.example.meet_api.controller;

import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.dto.Location.LocationCreateDto;
import com.example.meet_api.dto.Location.LookCreateDto;
import com.example.meet_api.repository.LocationRepository;
import com.example.meet_api.service.LookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/look")
@Slf4j
@RequiredArgsConstructor
public class LookRestController {

    private final LookService lookService;


    @PostMapping("/update")
    public ResponseEntity<? extends BaseResponse> updateLocation(@RequestBody LookCreateDto dto, HttpServletRequest request) {

        lookService.updateLook(dto);


        return ResponseEntity.ok().body(new CommonResponse<>("", "업데이트 되었습니다.", "200"));
    }

}
