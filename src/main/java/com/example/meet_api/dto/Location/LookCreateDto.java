package com.example.meet_api.dto.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LookCreateDto {

    // 인상 착의
    private Long id;
    private String hat;
    private String top;
    private String bottom;
    private String shoes;
    private String etc;
    private String outer;

    private String destinationDetailAddress;
    private Long locationId;
}
