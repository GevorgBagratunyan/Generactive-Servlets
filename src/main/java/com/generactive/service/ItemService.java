package com.generactive.service;

import com.generactive.model.Item;
import com.generactive.repository.CRUD;
import com.generactive.repository.ItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements CRUD<Item> {

    private final ItemRepository itemRepository;
    private final SessionFactory sessionFactory;

    public ItemService(ItemRepository itemRepository, SessionFactory sessionFactory) {
        this.itemRepository = itemRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Item create(Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Item it =  itemRepository.create(item);

        transaction.commit();
        session.close();

        return it;
    }

    @Override
    public Optional<Item> read(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Item> it =  itemRepository.read(id);

        transaction.commit();
        session.close();

        return it;
    }

    @Override
    public Optional<Item> update(Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Item> it =  itemRepository.update(item);

        transaction.commit();
        session.close();

        return it;
    }

    @Override
    public Optional<Item> delete(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Item> it =  itemRepository.delete(id);

        transaction.commit();
        session.close();

        return it;
    }

    public List<Item> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        List<Item> items =  itemRepository.getAll();

        transaction.commit();
        session.close();

        return items;
    }

    public List<Item> allByPriceRange(double from, double to) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        List<Item> items =  itemRepository.allByPriceRange(from, to);

        transaction.commit();
        session.close();

        return items;
    }

    public Optional<Item> getByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Item> it =  itemRepository.getByName(name);

        transaction.commit();
        session.close();

        return it;
    }

    public Optional<Item> setGroup(long itemID, long groupID) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Item> it =  itemRepository.setGroup(itemID, groupID);

        transaction.commit();
        session.close();

        return it;
    }
}
