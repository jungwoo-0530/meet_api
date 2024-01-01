package com.example.meet_api.controller;

import com.example.meet_api.domain.FileVO;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/member")
@Slf4j
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping(value = "/join")
    public ResponseEntity<? extends BaseResponse> join(@RequestBody MemberCreateDto dto,
                                                       HttpServletRequest request){

        if(memberService.isExistHandPhone(dto.getTelephone())){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "이미 가입된 회원입니다.", "204"));
        }

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


    @GetMapping("/check/name")
    public ResponseEntity<? extends BaseResponse> checkName(@RequestParam("name") String name,
                                                            HttpServletRequest request){

        boolean isExistName = memberService.isExistName(name);

        if(isExistName){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "이미 존재하는 이름입니다.", "204"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("", "사용 가능한 이름입니다.", "200"));
    }

    @PostMapping("/check/loginId")
    public ResponseEntity<? extends BaseResponse> checkLoginId(@RequestBody MemberCreateDto dto,
                                                               HttpServletRequest request){

        boolean isExistLoginId = memberService.isExistLoginId(dto.getLoginId());

        if(isExistLoginId){
            return ResponseEntity.status(200).body(new CommonResponse<>("", "이미 존재하는 아이디입니다.", "204"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("", "사용 가능한 아이디입니다.", "200"));
    }

    @GetMapping("/info")
    public ResponseEntity<? extends BaseResponse> getMemberInfo(@RequestParam("id") Long id){

            Member member = memberService.getMemberById(id);

            return ResponseEntity.ok().body(new CommonResponse<>(member, "데이터를 불러왔습니다.", "200"));
    }

    @PostMapping("/update")
    public ResponseEntity<? extends BaseResponse> updateMemberInfo(@RequestBody MemberCreateDto dto,
                                                                   HttpServletRequest request){

        Member member = memberService.updateMember(dto);


        return ResponseEntity.ok().body(new CommonResponse<>("", "회원정보가 수정되었습니다.", "200"));
    }

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<? extends BaseResponse> updateProfile(@RequestParam(value = "profileImg", required = false) MultipartFile profileImg,
                                                                @RequestParam(value = "loginId", required = false) String loginId,
                                                                HttpServletRequest request) throws IOException {

        String responseUri;

        if(profileImg != null && !profileImg.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + profileImg.getOriginalFilename();
            String fullPath = "/Users/jungwoo/Desktop/sources/meet/app/assets/profile/" + fileName;
            profileImg.transferTo(new File(fullPath));

            // 멤버 테이블 업데이트
            memberService.updateProfileImg(fileName, loginId);

            responseUri = fileName;
        }else{
            // 멤버 테이블 업데이트
            memberService.updateProfileImg("", loginId);

            responseUri = "";
        }

        return ResponseEntity.ok().body(new CommonResponse<>(responseUri, "프로필이 수정되었습니다.", "200"));
    }
}
