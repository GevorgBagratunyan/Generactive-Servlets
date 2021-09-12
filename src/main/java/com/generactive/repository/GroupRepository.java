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
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Group group = session.get(Group.class, ID);
        session.getTransaction().commit();
        return Optional.ofNullable(group);
    }

    @Override
    public Optional<Group> update(Group group) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Group updated = (Group) session.merge(group);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<Group> delete(long ID) {
        Optional<Group> optionalGroup;
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Group group = session.get(Group.class, ID);
        if (group != null) {
            session.delete(group);
            optionalGroup = Optional.of(group);
        } else optionalGroup = Optional.empty();
        session.getTransaction().commit();
        return optionalGroup;
    }

    public List<Group> getAll() {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        List<Group> groups = session.createQuery("FROM groups ", Group.class).list();
        session.getTransaction().commit();
        return groups;
    }

    public Optional<Group> setParent(long groupID, long parentID) {
        Session session = GeneractiveSessionFactory.getSession();
        session.beginTransaction();
        Group group = session.get(Group.class, groupID);
        Group parent = session.get(Group.class, parentID);
        group.setParent(parent);
        Group updated = (Group) session.merge(group);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }
}
