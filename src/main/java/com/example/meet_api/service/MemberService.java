package com.example.meet_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
//import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {


    /*public static String getSalt(String password) {

        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    public static String sha256WithSaltEncode(String password, String salt){

        return Hashing.sha256().hashString(password + salt, StandardCharsets.UTF_8).toString();
    }*/
}
