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

    @ManyToOne(fetch = FetchType.LAZY) //Order 엔티티 : Member 엔티티 = 다 : 1
    @JoinColumn(name="member_id")
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 1:1의 관계에서는 접근할 일이 많은 엔티티 쪽에 외래키를 두는 것을 선호한다. (Delivery 엔티티에 외래키를 두어도 상관없긴함)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간 : 자바8부터 지원되는 LocalDateTime 타입은 하이버네이트가 자동으로 변환해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==//
    //양방향 연관관계일 때 양쪽의 엔티티 필드값을 원자적으로 한 번에 셋팅해주는 메서드
    public void setMember(Member member) {
        this.member = member; //Order 엔티티 필드값 설정
        member.getOrders().add(this); //Member 엔티티 필드값 설정
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); //Order 엔티티 필드값 설정
        orderItem.setOrder(this); //OrderItem 엔티티 필드값 설정
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }



}
