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

    @Column(name = "NAME", nullable = false, length = 6)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "TELEPHONE", nullable = false, length = 11)
    private Integer telephone;
//    private String role;

    @Column(name = "PROFILE_IMG", nullable = true)
    private String imgUri;

    public void changeTelephoneAndEmail(String email, Integer telephone){
        this.email = email;
        this.telephone = telephone;
    }

    /*public void changeRole(String role) {
        this.role = role;
    }*/

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeImgUri(String imgUri){
        this.imgUri = imgUri;
    }


}
