package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 -> 부모쪽에서 테이블 구현 전략을 설정
@DiscriminatorColumn(name= "dtype") //싱글 테이블이기 때문에 구분 컬럼을 지정
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity; //재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//
    //도메인 주도 개발 : 재고수량을 필드로 갖는 클래스 내부에 재고와 관련된 비지니스 로직을 구현한다.
    public void addStock(int quantity) { //재고 증가
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) { //재고 감소
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
