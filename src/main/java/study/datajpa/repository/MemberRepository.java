package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
// Data JPA 사용시 "JpaRepository<엔티티,엔티티의 키값>" 상속받아 사용한다.
public interface MemberRepository extends JpaRepository<Member,Long> ,MemberRepositoryCustom{

    // 메소드 명으로 JPA 를 사용하여 쿼리 실행
    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);

    //쿼리를 직접 구현하여 실행한다.
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    //DTO 로 변환하여 조회 시 아래 쿼리처럼 구현한다.
    @Query("select new study.datajpa.dto.MemberDto(m.Id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); // 단건

    Optional<Member> findOptionalByUsername(String username); // 단건 optional

    //count 쿼리를 따로 실행 할 수 있다.
    @Query(value="select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
//      Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) //<- update 할 시 꼭 넣어주어야 한다. clearAutomatically = true < 필수 X clear 가 필요할 시
    @Query("update Member m set m.age = m.age+1 where m.age <= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // DataJpa 에서 fetch 조인을 하기 위한 설정
    List<Member> findAll();

    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findByUsername(@Param("username") String username);


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // 말 그대로 해당 기능 사용시 읽기전용 으로 설정
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

//    List<UsernameOnly> findProjectionsByUsername(@Param("username") String username);

    <T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);

    @Query(value = "select * from member where username = ?",nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id , m.username,t.name as teamName " +
            "from member m left join team t"
            ,countQuery = "select count(*) from member"
            ,nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);

}
