package com.example.meet_api.service;

import com.example.meet_api.domain.Member;
import com.example.meet_api.dto.member.MemberCreateDto;
import com.example.meet_api.dto.member.MemberLoginDto;
import com.example.meet_api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public Long addMember(MemberCreateDto dto){

        Member newMember = Member.builder()
                .loginId(dto.getLoginId())
                .password(encodePassword(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .imgUri(dto.getImgUri())
                .build();

        memberRepository.save(newMember);

        return newMember.getId();
    }

    @Transactional(readOnly = true)
    public Member getMemberByLoginId(MemberLoginDto dto){

        return memberRepository.findByLoginId(dto.getLoginId());
    }


    public String encodePassword(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);

    }

    public boolean matchPassword(String password, String encodedPassword){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, encodedPassword);

    }

}
