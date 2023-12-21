package com.example.meet_api.repository;

import com.example.meet_api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Member findByLoginId(String loginId);

    Member findByEmail(String email);

}
