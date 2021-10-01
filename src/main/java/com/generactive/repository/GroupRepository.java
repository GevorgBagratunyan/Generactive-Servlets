package com.generactive.repository;

import com.generactive.model.Group;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class GroupRepository implements RepositoryCRUD<Group> {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public GroupRepository() {
        entityManagerFactory  = Persistence.createEntityManagerFactory("Generactive-JPA");
        entityManager = entityManagerFactory.createEntityManager();
    }



    @Override
    public Group create(Group group) {

        entityManager.persist(group);
        return group;
    }

    @Override
    public Optional<Group> read(Long id) {

        Group group = entityManager.find(Group.class, id);
        return Optional.ofNullable(group);
    }

    @Override
    public void update(Group group) {

        entityManager.merge(group);
    }

    @Override
    public void delete(Long id) {

        Group group = entityManager.find(Group.class, id);
        if (group != null) {
            entityManager.remove(group);
        }
    }

    public List<Group> getAll() {

        return entityManager
                .createQuery("SELECT g FROM groups g", Group.class)
                .getResultList();
    }

    public Group getByName(String name) {

        return entityManager
                .createQuery("SELECT g FROM groups g WHERE g.name='" + name + "'", Group.class)
                .getSingleResult();
    }

    @Transactional
    public void setParent(Long groupId, Long parentId) {

        Group group = entityManager.find(Group.class, groupId);
        if(group==null) {
            throw new NoSuchElementException("No such group found by id: " + groupId);
        }
        Group parent = entityManager.find(Group.class, parentId);
        if(parent==null) {
            throw new NoSuchElementException("No such parent group found by id: " + parentId);
        }
        group.setParent(parent);
        entityManager.merge(group);
    }
}
