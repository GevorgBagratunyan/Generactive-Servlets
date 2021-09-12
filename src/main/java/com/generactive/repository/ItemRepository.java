package com.generactive.repository;

import com.generactive.model.Group;
import com.generactive.model.Item;
import com.generactive.util.GeneractiveSessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ItemRepository implements CRUD<Item> {
    @Override
    public Item create(Item item) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        long id = (long) session.save(item);
        Item saved = session.get(Item.class, id);
        session.getTransaction().commit();
        return saved;
    }

    @Override
    public Optional<Item> read(long ID) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Item item = session.get(Item.class, ID);
        session.getTransaction().commit();
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Item> update(Item item) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Item updated = (Item) session.merge(item);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }


    @Override
    public Optional<Item> delete(long ID) {
        Optional<Item> optionalItem;
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Item item = session.get(Item.class, ID);
        if (item != null) {
            session.delete(item);
            optionalItem = Optional.of(item);
        } else optionalItem = Optional.empty();
        session.getTransaction().commit();
        return optionalItem;
    }

    public Optional<Item> setGroup(long itemID, long groupID) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Group group = session.get(Group.class, groupID);
        Item item = session.get(Item.class, itemID);
        item.setGroup(group);
        Item updated = (Item) session.merge(item);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }

    public List<Item> getAll() {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("FROM item", Item.class).list();
        session.getTransaction().commit();
        return items;
    }

    public List<Item> allByPriceRange(double from, double to) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("FROM item " +
                "WHERE base_price BETWEEN " + from + " AND " + to, Item.class).list();
        session.getTransaction().commit();
        return items;
    }

    public Optional<Item> getByName(String name) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Query<Item> query = session.createQuery("FROM item i WHERE i.name=:name", Item.class);
        query.setParameter("name", name);
        Item item = query.uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(item);
    }
}
