package com.example.meet_api.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private Long id;

    @Column(name = "LOCATION_ID", nullable = false)
    private Long locationId;

    @Column(name = "OWNER_ID", nullable = false)
    private String ownerId;

    @Column(name = "OTHER_ID", nullable = false)
    private String otherId;

    @Column(name = "UES_YN", nullable = false)
    private String useYn;

    @Column(name = "STATUS", nullable = false)
    private String status;

}
