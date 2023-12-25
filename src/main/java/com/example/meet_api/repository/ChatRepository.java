package com.example.meet_api.repository;


import com.example.meet_api.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c where c.locationId = :locationId")
    Optional<Chat> findByLocationId(Long locationId);


    @Query("select c from Chat c where (c.ownerId = :loginId or c.otherId = :loginId) and c.useYn = 'Y' order by c.id desc")
    List<Chat> findAllByLoginId(String loginId);


}
