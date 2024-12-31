package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //Order 엔티티 : Member 엔티티 = 다 : 1
    @JoinColumn(name="member_id") //테이블과 매핑할 FK 지정
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

    //==생성 메서드==//
    //연관관계가 복잡하게 얽힌 엔티티는 별도의 생성 메서드를 만드는 것이 좋다.
    //기존처럼 객체 생성 후, Setter로 값을 설정하는 것이 아니라, 엔티티 생성 시점에서 '주문'에 대한 응집된 비지니스 로직을 거쳐 객체를 생성하게 한다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //OrderItem 을 먼저 생성하고 이 메서드를 호출하게 된다.
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem); //연관관계 편의 메서드: OrderItem 엔티티의 필드값도 같이 셋팅됨
        }
        order.setDelivery(delivery);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    //==비지니스 로직==//
    /*
        주문 취소: order.cancel()
    */
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL); //order 엔티티의 status 속성 값 변경
        
        for (OrderItem orderItem : this.orderItems) { //this : 생략가능
            orderItem.cancel(); //주문 상품 엔티티에서 재고수량을 증가시키는 메서드 호출
        }
    }

    //==조회 로직==//
    /*
        전체 주문 가격 조회
    */
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice(); //주문 상품 엔티티에 주문 수량과 주문 가격이 있기 때문에, 주문 엔티티에서 계산한다.
        }

        return totalPrice;
        //return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum(); //위 코드를 Stream을 통해 간략화 가능함
    }



}
