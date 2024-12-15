package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다") //필수 속성으로 지정
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
