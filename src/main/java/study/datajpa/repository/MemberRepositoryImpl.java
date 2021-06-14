package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor  //MemberRepositoryImpl -> 끝에 Impl 을 꼭 맞춰주어야 한다.
public class MemberRepositoryImpl implements MemberRepositoryCustom{


    private final EntityManager em;


    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
