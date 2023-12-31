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
    public Member getMemberByLoginId(String loginId){

        return memberRepository.findByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public boolean isExistName(String name){

        return memberRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Member getMemberById(Long id){

        return memberRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public Member updateMember(MemberCreateDto dto){

        Member member = memberRepository.findById(dto.getId()).orElseThrow(NullPointerException::new);

        member.setName(dto.getName());
        if(!dto.getPassword().isEmpty()) {
            member.setPassword(encodePassword(dto.getPassword()));
        }

        // chat


        // invite_info

        // location

        // location_detail

        // look

        return member;
    }



    public String encodePassword(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);

    }



    public boolean matchPassword(String password, String encodedPassword){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, encodedPassword);

    }

    @Transactional
    public void updateProfileImg(String fileName, String loginId) {

            Member member = memberRepository.findByLoginId(loginId);

            member.setImgUri(fileName);
    }

    @Transactional(readOnly = true)
    public boolean isExistLoginId(String loginId) {
        return memberRepository.countByLoginId(loginId) > 0;
    }

    @Transactional(readOnly = true)
    public boolean isExistHandPhone(String telephone) {

        return memberRepository.countByTelephone(telephone) > 0;
    }
}