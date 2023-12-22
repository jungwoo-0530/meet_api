package com.example.meet_api.controller;

import com.example.meet_api.domain.Member;
import com.example.meet_api.dto.BaseResponse;
import com.example.meet_api.dto.CommonResponse;
import com.example.meet_api.dto.member.MemberCreateDto;
import com.example.meet_api.dto.member.MemberLoginDto;
import com.example.meet_api.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping(value = "/join")
    public ResponseEntity<? extends BaseResponse> join(@RequestBody MemberCreateDto dto,
                                                       HttpServletRequest request){

        Long memberId = memberService.addMember(dto);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(memberId, "회원가입이 완료되었습니다.", "200"));

    }

    @PostMapping("/login")
    public ResponseEntity<? extends BaseResponse> login(@RequestBody MemberLoginDto dto,
                                                        HttpServletRequest request){

        Member member = memberService.getMemberByLoginId(dto.getLoginId());

        if(member == null){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "존재하지 않는 아이디입니다.", "204"));
        }

        boolean matchPassword = memberService.matchPassword(dto.getPassword(), member.getPassword());

        if(!matchPassword){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "비밀번호가 일치하지 않습니다.", "204"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(member, "로그인에 성공하였습니다.", "200"));
    }

    @GetMapping("/alarm")
    public ResponseEntity<? extends BaseResponse> alarm(HttpServletRequest request){

        return ResponseEntity.ok().body(new CommonResponse<>("test", "test", "200"));
    }

}
