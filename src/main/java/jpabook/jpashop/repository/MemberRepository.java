package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //final 필드에 대한 매개변수 생성자 작성 -> 매개변수 생성자에 @Autowired가 적용 -> 생성자에서 EntityManager 주입 가능
//단, @Autowired로 EntityManager를 주입하는 것은 SpringBoot Data JPA를 쓰고 있을 때 가능한 것임
//원래는 @PersistenceContext로 EntityManager를 의존성 주입해야 한다.
public class MemberRepository {

    //@PersistenceContext //스프링이 자동 설정한 JPA의 EntityManager를 주입하도록 함
    private final EntityManager em;

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
