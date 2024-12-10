package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne //Order 엔티티 : Member 엔티티 = 다 : 1
    @JoinColumn(name="member_id")
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne // 1:1의 관계에서는 접근할 일이 많은 엔티티 쪽에 외래키를 두는 것을 선호한다. (Delivery 엔티티에 외래키를 두어도 상관없긴함)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간 : 자바8부터 지원되는 LocalDateTime 타입은 하이버네이트가 자동으로 변환해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
