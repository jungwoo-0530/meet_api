package com.example.meet_api.dto.Chat;

import com.example.meet_api.domain.Chat;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatCreateDto {

    Long locationId;
    String ownerId;
    String otherId;
    String useYn;
    String status; // Y: 수락 N: 거절

    public Chat toEntity() {
        return Chat.builder()
                .locationId(locationId)
                .ownerId(ownerId)
                .otherId(otherId)
                .useYn(useYn)
                .status(status)
                .build();
    }
}
