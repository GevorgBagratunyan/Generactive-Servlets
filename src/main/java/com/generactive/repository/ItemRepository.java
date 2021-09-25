package com.generactive.repository;

import com.generactive.model.Group;
import com.generactive.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository implements CRUD<Item> {

    private final SessionFactory sessionFactory;

    public ItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Item create(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = (long) session.save(item);
        Item saved = session.get(Item.class, id);
        session.getTransaction().commit();
        return saved;
    }

    @Override
    public Optional<Item> read(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Item> update(Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item updated = (Item) session.merge(item);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }


    @Override
    public Optional<Item> delete(long id) {
        Optional<Item> optionalItem;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        if (item != null) {
            session.delete(item);
            optionalItem = Optional.of(item);
        } else optionalItem = Optional.empty();
        session.getTransaction().commit();
        return optionalItem;
    }

    public Optional<Item> setGroup(long itemId, long groupId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Group group = session.get(Group.class, groupId);
        Item item = session.get(Item.class, itemId);
        item.setGroup(group);
        Item updated = (Item) session.merge(item);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }

    public List<Item> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("FROM item", Item.class).list();
        session.getTransaction().commit();
        return items;
    }

    public List<Item> allByPriceRange(double from, double to) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("FROM item " +
                "WHERE base_price BETWEEN " + from + " AND " + to, Item.class).list();
        session.getTransaction().commit();
        return items;
    }

    public Optional<Item> getByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Item> query = session.createQuery("FROM item i WHERE i.name=:name", Item.class);
        query.setParameter("name", name);
        Item item = query.uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(item);
    }
}
