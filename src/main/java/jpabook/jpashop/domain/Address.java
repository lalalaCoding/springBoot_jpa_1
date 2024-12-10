package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable //내장 타입 명시 어노테이션
@Getter //값 타입이기 때문에 변경이 불가능하도록 하기위해 @Setter는 사용X
public class Address {

    private String city;
    private String street;
    private String zipCode;

    //JPA 스펙 상 기본 생성자는 강제로 명시해야 한다.
    protected Address() {}

    public Address(String zipCode, String street, String city) {
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
    }
}
