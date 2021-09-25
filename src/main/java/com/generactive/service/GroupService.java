package com.generactive.service;

import com.generactive.model.Group;
import com.generactive.repository.CRUD;
import com.generactive.repository.GroupRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class GroupService implements CRUD<Group> {

    private final GroupRepository groupRepository;
    private final SessionFactory sessionFactory;

    public GroupService(GroupRepository groupRepository, SessionFactory sessionFactory) {
        this.groupRepository = groupRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Group create(Group group) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Group gr = groupRepository.create(group);

        transaction.commit();
        session.close();

        return gr;
    }

    @Override
    public Optional<Group> read(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Group> gr = groupRepository.read(id);

        transaction.commit();
        session.close();

        return gr;
    }

    @Override
    public Optional<Group> update(Group group) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Group> gr = groupRepository.update(group);

        transaction.commit();
        session.close();

        return gr;
    }

    @Override
    public Optional<Group> delete(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Group> gr = groupRepository.delete(id);

        transaction.commit();
        session.close();

        return gr;
    }

    public List<Group> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        List<Group> groups = groupRepository.getAll();

        transaction.commit();
        session.close();

        return groups;
    }

    public Optional<Group> getByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Group> gr = groupRepository.getByName(name);

        transaction.commit();
        session.close();

        return gr;
    }

    public Optional<Group> setParent(long groupId, long parentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        session.beginTransaction();

        Optional<Group> gr = groupRepository.setParent(groupId, parentId);

        transaction.commit();
        session.close();

        return gr;
    }
}
