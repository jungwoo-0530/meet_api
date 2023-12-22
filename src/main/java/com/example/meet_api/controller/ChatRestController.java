package com.example.meet_api.controller;

import com.example.meet_api.domain.Chat;
import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;


    @GetMapping("/list")
    public ResponseEntity<? extends BaseResponse> list(@RequestParam("id") String loginId, HttpServletRequest request){

        List<Chat> chatList = chatService.getChatListByLoginId(loginId);

        if(chatList.isEmpty()){
            return ResponseEntity.ok().body(new CommonResponse<>(chatList, "채팅 목록이 없습니다.", "200"));
        }

        return ResponseEntity.ok().body(new CommonResponse<>(chatList, "채팅 목록을 불러왔습니다.", "200"));

    }

}
