package com.example.meet_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteInfo extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVITE_INFO_ID")
    private Long id;

    @Column(name = "LOCATION_ID", nullable = false)
    private Long locationId;

    @Column(name = "INVITEE_ID", nullable = false)
    private String inviteeId;

    @Column(name = "INVITER_ID", nullable = false)
    private String inviterId;

}
