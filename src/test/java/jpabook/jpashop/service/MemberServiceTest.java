package jpabook.jpashop.service;


import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/*
    플러시? : 영속성 컨텍스트에 있는 변경이나 등록 내용을 DB에 반영하는 것
    데이터베이스 트랜젝션이 commit 되는 순간에 플러시가 되면서 JPA 영속성 컨텍스트에 있는 멤버 객체가 insert문으로 작성되어, DB에 저장된다.
    따라서, DB에 전달되는 쿼리를 로그로 확인하고 싶다면 다음 방법 중 하나를 사용한다.
    @Rollback(false) : @Transactional이 항상 Commit 하도록 실행
    em.flush() : 플러시 실행
*/
@RunWith(SpringRunner.class) //스프링과 JUnit 테스트를 통합하는 어노테이션 -> JUnit 5에서는 @SpringBootTest에 이미 내장되어 있음
@SpringBootTest //스프링 부트를 띄운 상태로 테스트 환경 작동
@Transactional //테스트에서의 @Transactional은 Commit을 하지 않고 Rollback을 함
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush();
        //JPA에서는 같은 트랜잭션 안에서 엔티티의 PK 값이 같으면 같은 영속성 컨텍스트에서 동일한 객체로 관리된다.
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) //기대되는 예외 클래스 메타정보를 지정
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다!!

        //then
        Assert.fail("예외가 발생해야 한다."); //예외가 발생하지 않았을 때 현재 라인의 코드가 실행됨
    }
}