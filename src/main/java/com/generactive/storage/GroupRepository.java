package com.generactive.storage;


import com.generactive.model.Group;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GroupRepository implements CRUD<Group> {

    @Override
    public Group create(Group group) {
        return Storage.addGroup(group);
    }

    @Override
    public Optional<Group> read(int id) {
        return Storage.findGroupByID(id);
    }

    //This will be implemented soon, after realizing REST requests
    @Override
    public Optional<Group> update(int id, Group group) {
        return null;
    }

    @Override
    public Optional<Group> delete(int id) {
        return Storage.removeGroupByID(id);
    }

    //In this method return is not Optional, for testing purposes
    public Group getByName(String name) throws NoSuchElementException {
            return Storage.findGroupByName(name);
    }

    public List<Group> getSubgroupsByParent(Group parent) {
        return Storage.findSubGroupsByParent(parent);
    }

    public void printAll() {
        Storage.printAllGroups();
    }
}
