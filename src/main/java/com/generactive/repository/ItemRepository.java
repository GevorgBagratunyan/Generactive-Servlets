package com.generactive.repository;

import com.generactive.model.Group;
import com.generactive.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ItemRepository implements RepositoryCRUD<Item> {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Generactive-JPA");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public Item create(Item item) {

        entityManager.persist(item);
        return item;
    }

    @Override
    public Optional<Item> read(Long id) {

        Item item = entityManager.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    @Override
    public void update(Item item) {
        entityManager.merge(item);
    }


    @Override
    public void delete(Long id) {

        Item item = read(id)
                .orElseThrow(NoSuchElementException::new);
        entityManager.remove(item);
    }

    @Transactional
    public void setGroup(Long itemId, Long groupId) {
        Group group = entityManager.find(Group.class, groupId);
        Item item = entityManager.find(Item.class, itemId);
        item.setGroup(group);
        entityManager.merge(item);
    }

    public List<Item> getAll() {
        return entityManager.createQuery("SELECT i FROM item i", Item.class).getResultList();
    }

    public List<Item> allByPriceRange(double from, double to) {

        return entityManager.createQuery("SELECT i FROM item i" +
                "WHERE base_price BETWEEN " + from + " AND " + to, Item.class).getResultList();
    }

    public Item getByName(String name) {
        return entityManager.createQuery("SELECT i FROM item i WHERE i.name='" + name + "'", Item.class).getSingleResult();
    }
}
