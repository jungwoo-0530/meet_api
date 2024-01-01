package com.example.meet_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Look extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOOK_ID")
    private Long id;

    @Column(name = "LOCATION_ID", nullable = false)
    private Long locationId;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "HAT", nullable = true)
    private String hat;

    @Column(name = "TOP", nullable = true)
    private String top;

    @Column(name = "BOTTOM", nullable = true)
    private String bottom;

    @Column(name = "SHOES", nullable = true)
    private String shoes;

    @Column(name = "ETC", nullable = true)
    private String etc;

    @Column(name = "OUTER_CLOTH", nullable = true)
    private String outerCloth;

}
