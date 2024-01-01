package com.example.meet_api.repository;

import com.example.meet_api.domain.Look;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LookRepository extends JpaRepository<Look, Long> {

    @Query("select l from Look l where l.locationId = :locationId and l.loginId = :loginId")
    public Optional<Look> getLookByLocationIdAndLoginId(Long locationId, String loginId);

    @Transactional
    @Modifying
    @Query("update Look l set l.hat = :hat, l.outerCloth = :outer, l.top = :top, l.bottom = :bottom, l.shoes = :shoes, l.etc = :etc where l.id = :id")
    public void updateLook(Long id, String hat, String top, String bottom, String shoes, String etc, String outer);

    @Modifying
    @Query("delete from Look l where l.locationId = :locationId")
    public void deleteLookByLocationId(Long locationId);
}
