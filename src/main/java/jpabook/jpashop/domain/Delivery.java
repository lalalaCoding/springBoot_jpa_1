package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)// ENUM 타입을 사용할 때는 반드시 @Enumerated을 적용해야 함.
                                // Enum의 타입의 기본값은 ORDINAL(숫자)인데 꼭 STRING으로 써야함 (ENUM에 데이터가 추가되더라도 DB가 꼬이지 않을 수 있기 때문!)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]

}
