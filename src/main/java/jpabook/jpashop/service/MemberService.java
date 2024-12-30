package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 조회 시에 JPA의 성능을 최적화해준다.
// org.springframework.transaction.annotation에 있는 @Transactional 어노테이션을 권장 : 사용 가능 옵션이 더 많기 때문
@RequiredArgsConstructor //final 필드만을 가지고 매개변수 생성자 작성해주는 어노테이션 -> 생성자에서 final 필드에 대한 의존성 주입이 이루어짐
public class MemberService {
    
    //final : 컴파일 시점에서 주입이 잘 되었는지 확인 가능하므로 사용 권장
    private final MemberRepository memberRepository;

    //== 회원 가입 ==//
    @Transactional //JPA에서 데이터 변경은 무조건 @Transactional 안에서 수행되어야 함(원칙)
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증(비지니스 로직)
        memberRepository.save(member); //영속성 컨텍스트에 member 객체를 저장할 때, PK 필드가 키 값이 된다. 따라서 (DB에 저장하기 이전에) PK 필드에 값이 채워진다.
        return member.getId();
    }

    //== 이름 중복 검사 ==//
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
