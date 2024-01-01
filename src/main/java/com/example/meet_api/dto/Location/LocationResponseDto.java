package com.example.meet_api.dto.Location;

import com.example.meet_api.domain.Look;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {

    private Long id;
    private String ownerId;
    private String otherId;
    private String ownerLatitude;
    private String ownerLongitude;
    private String ownerAddress;
    private String otherLatitude;
    private String otherLongitude;
    private String otherAddress;
    private String destinationLatitude;
    private String destinationLongitude;
    private String destinationAddress;
    private String destinationDetailAddress;
    private String chatRoomId;
    private String status;
    private String useYn;

    // 현재 위치
    private String currentMyLatitude;
    private String currentMyLongitude;
    private String currentMyAddress;

    private String currentOtherLatitude;
    private String currentOtherLongitude;
    private String currentOtherAddress;

    // 인상 착의 Look
    private long myLookId;
    private String myHat;
    private String myTop;
    private String myBottom;
    private String myShoes;
    private String myEtc;
    private String myOuter;

    private long otherLookId;
    private String otherHat;
    private String otherTop;
    private String otherBottom;
    private String otherShoes;
    private String otherEtc;
    private String otherOuter;

    public void setMyLook(Look look) {
        this.myLookId = look.getId();
        this.myHat = look.getHat();
        this.myTop = look.getTop();
        this.myBottom = look.getBottom();
        this.myShoes = look.getShoes();
        this.myEtc = look.getEtc();
        this.myOuter = look.getOuterCloth();
    }

    public void setOtherLook(Look look) {
        this.otherLookId = look.getId();
        this.otherHat = look.getHat();
        this.otherTop = look.getTop();
        this.otherBottom = look.getBottom();
        this.otherShoes = look.getShoes();
        this.otherEtc = look.getEtc();
        this.otherOuter = look.getOuterCloth();
    }
}
