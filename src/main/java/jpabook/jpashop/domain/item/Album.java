package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") //싱글 테이블에서의 구분 컬럼의 값을 지정
@Getter @Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
