package com.example.meet_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "LOGIN_ID", nullable = false, length = 10)
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME", nullable = true, length = 6)
    private String name;

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @Column(name = "TELEPHONE", nullable = false, length = 11)
    private String telephone;

    @Column(name = "PROFILE_IMG", nullable = true)
    private String imgUri;

    public void changeTelephoneAndEmail(String email, String telephone){
        this.email = email;
        this.telephone = telephone;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeImgUri(String imgUri){
        this.imgUri = imgUri;
    }


}