package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
/*
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // 엔티티매니저를 통한 모든 데이터 변경은 항상 트랙젝션 안에서 이루어져야 한다.
    //가급적 org. springframework. transaction. annotation 안에 포함된 @Transactional 사용을 권장 : 사용가능한 옵션이 더 많기 때문임
    @Rollback(false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // @Entity 클래스에서 @Id가 붙은 필드 값을 통해 객체를 비교하므로 true
    }
*/



}