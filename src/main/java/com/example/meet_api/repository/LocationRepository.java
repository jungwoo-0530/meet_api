package com.example.meet_api.repository;

import com.example.meet_api.domain.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{



    @Query("select l from Location l where l.useYn = 'Y' and ((l.status = 'W' and l.ownerId = :ownerId) or (l.status != 'W' and (l.ownerId = :ownerId or l.otherId = :ownerId))) order by l.id desc")
    List<Location> findAllByOwnerId(@Param("ownerId") String ownerId);

    @Query("select count(l) from Location l where ((l.ownerId = :ownerId and l.otherId = :otherId) or (l.ownerId = :otherId and l.otherId = :ownerId)) and l.useYn = 'Y' and l.status in ('A' , 'W')")
    int findLocationByOwnerIdAndOtherId(@Param("ownerId") String ownerId, @Param("otherId") String otherId);
}


