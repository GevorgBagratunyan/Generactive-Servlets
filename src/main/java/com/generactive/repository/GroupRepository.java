package com.generactive.repository;

import com.generactive.model.Group;
import com.generactive.util.GeneractiveSessionFactory;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class GroupRepository implements CRUD<Group> {

    @Override
    public Group create(Group group) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        long id = (long) session.save(group);
        Group saved = session.get(Group.class,id);
        session.getTransaction().commit();
        return saved;
    }

    @Override
    public Optional<Group> read(long ID) {
        return Optional.empty();
    }

    @Override
    public Optional<Group> update(Group group) {
        return Optional.empty();
    }

    @Override
    public Optional<Group> delete(long ID) {
        return null;
    }

    public List<Group> getAll() {
        return null;
    }
}
