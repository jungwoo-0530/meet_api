package com.example.meet_api.repository;

import com.example.meet_api.domain.Location.LocationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationDetailRepository extends JpaRepository<LocationDetail, Long> {

    @Query("update LocationDetail l set l.latitude = :latitude, l.longitude = :longitude where l.locationId = :locationId and l.loginId = :loginId")
    public void updateLocationDetail(Long locationId, String loginId, String latitude, String longitude);

    @Query("select l from LocationDetail l where l.locationId = :locationId and l.loginId = :loginId")
    public Optional<LocationDetail> getLocationDetailByLocationIdAndLoginId(Long locationId, String loginId);
}
