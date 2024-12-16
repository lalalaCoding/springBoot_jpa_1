package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // 신규로 생성한 Item 객체인 경우 (영속성 컨텍스트에 동일한 Item 객체가 없음) -> 등록 해야함.
            em.persist(item);
        } else { //DB에 등록된 Item 객체가 영속성 컨텍스트에 들어있는 경우 -> 수정 해야함.
            em.merge(item); //준영속 상태의 엔티티 -> 양속 상태의 엔티티로 변경
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
