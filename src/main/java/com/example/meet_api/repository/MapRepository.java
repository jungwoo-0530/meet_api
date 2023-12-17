package com.example.meet_api.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository {


}


/*
//@Repository
//public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
//
//
//    boolean existsByLoginId(String loginId);
//
//    boolean existsByEmail(String email);
//
//    Optional<Member> findByLoginId(String loginId);

    @Query("select b from Board b left join fetch b.member where b.id = :id")
    Optional<Board> findByBoardIdWithMember(@Param("id") Long boardId);
//
//}*/
