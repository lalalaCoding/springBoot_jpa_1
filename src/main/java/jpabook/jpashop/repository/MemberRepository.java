package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext //스프링이 자동 설정한 JPA의 EntityManager를 주입하도록 함
    private EntityManager em;

/*
    @PersistenceUnit //스프링이 자동 설정한 JPA의 EntityManagerFactory를 주입 (스프링 내에서는 거의 사용X)
    private EntityManagerFactory emf;
*/

    public void save(Member member) {
        em.persist(member); //영속성 컨텍스트에 member 객체를 저장 -> 추후 트랙잭션이 commit될 때 db에 반영됨
    }

    public Member findOne(Long id) { //단건 조회
        return em.find(Member.class, id); //find(type, pk)
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //createQuery(JPQL, type)
                .getResultList();
    }

    public List<Member> findByName(String name) { //이름으로 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
