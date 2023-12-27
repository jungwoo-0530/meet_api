package com.example.meet_api.domain.Location;

import com.example.meet_api.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID")
    private Long id;

    @Column(name = "OWNER_ID", nullable = false)
    private String ownerId;

    @Column(name = "OTHER_ID", nullable = false)
    private String otherId;

    @Column(name = "OWNER_LATITUDE", nullable = false)
    private String ownerLatitude;

    @Column(name = "OWNER_LONGITUDE", nullable = false)
    private String ownerLongitude;

    @Column(name = "OWNER_ADDRESS", nullable = false)
    private String ownerAddress;

    @Column(name = "OTHER_LATITUDE", nullable = true)
    private String otherLatitude;

    @Column(name = "OTHER_LONGITUDE", nullable = true)
    private String otherLongitude;

    @Column(name = "OTHER_ADDRESS", nullable = true)
    private String otherAddress;

    @Column(name = "DESTINATION_LATITUDE", nullable = false)
    private String destinationLatitude;

    @Column(name = "DESTINATION_LONGITUDE", nullable = false)
    private String destinationLongitude;

    @Column(name = "DESTINATION_ADDRESS", nullable = false)
    private String destinationAddress;

    @Column(name = "CHAT_ROOM_ID", nullable = true)
    private String chatRoomId;

    // W : 대기중, A : 수락, S : 완료, C : 취소
    @Column(name = "STATUS", nullable = true)
    private String status;

    @Column(name = "USE_YN", nullable = false)
    private String useYn;

}