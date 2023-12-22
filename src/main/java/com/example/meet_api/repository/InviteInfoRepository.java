package com.example.meet_api.repository;

import com.example.meet_api.domain.InviteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteInfoRepository extends JpaRepository<InviteInfo, Long> {


    @Query("select i from InviteInfo i where i.inviteeId = :inviteeId order by i.id desc")
    List<InviteInfo> findAllByInviteeId(@Param("inviteeId") String inviteeId);
}
