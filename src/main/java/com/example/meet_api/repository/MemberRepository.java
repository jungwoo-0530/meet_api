package com.example.meet_api.repository;

import com.example.meet_api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Member findByLoginId(String loginId);

    Member findByEmail(String email);

    boolean existsByName(String name);

    @Query("select count(m) from Member m where m.loginId = :loginId")
    int countByLoginId(String loginId);

    @Query("select count(m) from Member m where m.telephone = :telephone")
    int countByTelephone(String telephone);
}
