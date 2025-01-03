package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어자를 Protected로 지정
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order; //주문
    
    private int orderPrice; //주문 당시의 가격
    private int count; //주문 당시의 수량

    //==생성 메서드==//
    //주문 가격 : Item 엔티티의 가격과 주문 가격은 쿠폰 등으로 인해 달라질 수 있기 때문에 별도의 매개변수로 전달받는것이 좋다.
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        this.getItem().addStock(count); //주문한 상품의 재고를 다시 늘려주어야 한다.
    }

    //==조회 로직==//
    /*
    *   주문상품 전체 가격 조회
    */
    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }

}
