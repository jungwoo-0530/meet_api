package com.example.meet_api.domain.Location;

import com.example.meet_api.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_DETAIL_ID")
    private Long id;

    @Column(name = "LOCATION_ID", nullable = false)
    private Long locationId;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "LATITUDE", nullable = false)
    private String latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private String longitude;


}