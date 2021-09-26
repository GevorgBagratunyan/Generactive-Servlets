package com.generactive.repository;

import com.generactive.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


@Resource
public class GroupRepository implements CRUD<Group> {

    private final SessionFactory sessionFactory;

    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Group create(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long id = (long) session.save(group);
        Group saved = session.get(Group.class,id);
        session.getTransaction().commit();
        return saved;
    }

    @Override
    public Optional<Group> read(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Group group = session.get(Group.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(group);
    }

    @Override
    public Optional<Group> update(Group group) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Group updated = (Group) session.merge(group);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }

    @Override
    public Optional<Group> delete(long id) {
        Optional<Group> optionalGroup;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Group group = session.get(Group.class, id);
        if (group != null) {
            session.delete(group);
            optionalGroup = Optional.of(group);
        } else optionalGroup = Optional.empty();
        session.getTransaction().commit();
        return optionalGroup;
    }

    public List<Group> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Group> groups = session.createQuery("FROM groups ", Group.class).list();
        session.getTransaction().commit();
        return groups;
    }

    public Optional<Group> getByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Group> query = session.createQuery("FROM groups g WHERE g.name=:name", Group.class);
        query.setParameter("name", name);
        Group group = query.uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(group);
    }

    public Optional<Group> setParent(long groupId, long parentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Group group = session.get(Group.class, groupId);
        Group parent = session.get(Group.class, parentId);
        group.setParent(parent);
        Group updated = (Group) session.merge(group);
        session.getTransaction().commit();
        return Optional.ofNullable(updated);
    }
}
