package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm()); // validation을 위해 빈 객체라도 들고 뷰로 이동하겠음
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        //@Valid : 객체에서 @NotEmpty와 같은 어노테이션을 검증해준다.
        //MemberForm에 대한 validation이 실패했을 때 발생하는 에러가 result에 바인딩된다.
        System.out.println("result = " + result.getObjectName());
        if (result.hasErrors()) {
            //스프링은 자동으로 BindingResult를 뷰로 전달해준다.
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member(); //엔티티 객체 생성 후 속성 값 셋팅
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        //실무에서는 화면을 출력하는데 필요한 DTO에 데이터를 저장한 이후에 View로 날리는 것을 추천한다.
        //API 에서는 절대 엔티티를 웹으로 전달해서는 안된다.
        //API는 하나의 고정된 스펙이기 때문이다.
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
