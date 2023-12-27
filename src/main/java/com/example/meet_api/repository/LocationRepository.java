package com.example.meet_api.repository;

import com.example.meet_api.domain.Location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{


    @Query("select l from Location l where (l.ownerId = :ownerId or l.otherId = :ownerId) and l.useYn = 'Y' order by l.id desc")
    List<Location> findAllByOwnerId(@Param("ownerId") String ownerId);

//    @Query("update Location l set l.own")

    /*@Query("select u from User u where u.username = :username")
    List<User> findUser(@Param("username") String username);*/
}


