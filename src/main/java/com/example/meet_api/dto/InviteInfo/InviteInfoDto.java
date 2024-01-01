package com.example.meet_api.dto.InviteInfo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteInfoDto {

    Long id;
    Long locationId;
    String inviteeId;
    String inviterId;
    String status; // Y: 수락 N: 거절

    String inviteeLatitude;
    String inviteeLongitude;
    String inviteeAddress;

    String inviterAddress;

    String destinationAddress;
    String destinationDetailAddress;

    String chatRoomId;

    String hat;
    String top;
    String bottom;
    String shoes;
    String etc;
    String outer;
}
