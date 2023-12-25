package com.example.meet_api.dto.InviteInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteInfoDto {

    Long id;
    Long locationId;
    String inviteeId;
    String inviterId;
    String status; // Y: 수락 N: 거절

    String inviteeLatitude;
    String inviteeLongitude;
    String inviteeAddress;

}
