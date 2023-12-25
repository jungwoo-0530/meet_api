package com.example.meet_api.dto.Location;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationCreateDto {

        private Long locationId;

        private String myLoginId;
        private String otherLoginId;

        private String ownerLatitude;
        private String ownerLongitude;
        private String ownerAddress;

        private String otherLatitude;
        private String otherLongitude;
        private String otherAddress;

        private String destinationLatitude;
        private String destinationLongitude;
        private String destinationAddress;

        private String useYn;
}
