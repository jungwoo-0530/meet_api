package com.example.meet_api.repository;

import com.example.meet_api.domain.Location.LocationDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationDetailRepository extends JpaRepository<LocationDetail, Long> {

    @Transactional
    @Modifying
    @Query("update LocationDetail ld set ld.latitude = :latitude, ld.longitude = :longitude where ld.locationId in (select l.id from Location l where l.useYn = 'Y' and l.status = 'A' and (l.ownerId = :loginId or l.otherId = :loginId)) and ld.loginId = :loginId")
    public void updateLocationDetail(String loginId, String latitude, String longitude);

    @Query("select l from LocationDetail l where l.locationId = :locationId and l.loginId = :loginId")
    public Optional<LocationDetail> getLocationDetailByLocationIdAndLoginId(Long locationId, String loginId);

    @Modifying
    @Query("delete from LocationDetail l where l.locationId = :locationId")
    public void deleteLocationDetailByLocationId (Long locationId);
}
