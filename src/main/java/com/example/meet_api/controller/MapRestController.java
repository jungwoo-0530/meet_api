package com.example.meet_api.controller;

import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.service.MapService;
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

    private final MapService mapService;


    @GetMapping("/test")
    public ResponseEntity<? extends BaseResponse> test(HttpServletRequest request) {
        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }

    @PostMapping("/room")
    public ResponseEntity<? extends BaseResponse> createRoom(HttpServletRequest request) {
        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }
}
