package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.team = " + member.getTeam());
        }
    }


    @Test
    public void JpaEventBaseEntity()  throws Exception{
        //given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        System.out.println("findMember,get = " + findMember.getCreatedDate());
        System.out.println("findMember,get = " + findMember.getLastModifiedDate());

    }

    @Test
    public void LoadingTest(){

       //테스트용 데이터를 입력합니다.
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        Team teamC = new Team("teamC");
        em.persist(teamA);
        em.persist(teamB);
        em.persist(teamC);

        Member member = new Member("memberA");
        Member memberB = new Member("memberB");
        Member memberC = new Member("memberC");

        member.changeTeam(teamA);
        memberB.changeTeam(teamB);
        memberC.changeTeam(teamC);

        em.persist(member);
        em.persist(memberB);
        em.persist(memberC);

        //초기화
        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member findMember : members) {
            System.out.println(" ======================================================= " );
            System.out.println("findMember.Team = " + findMember.getTeam());
            System.out.println(" ======================================================= " );

        }
    }
}