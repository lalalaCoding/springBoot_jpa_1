package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입 사용 어노테이션
    private Address address;

    @OneToMany(mappedBy = "member") //Member 엔티티 : Order 엔티티 = 1 : 다, 연관관계의 주인이 아닌 거울로 지정
    private List<Order> orders = new ArrayList<>();
}
