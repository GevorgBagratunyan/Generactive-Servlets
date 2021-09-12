package com.generactive.repository;

import com.generactive.model.User;

import java.util.Optional;

public class UserRepository implements CRUD<User> {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> read(long ID) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(long ID) {
        return null;
    }

    public boolean userIsExist(String username,String password) {
        return true;
    }

    public Optional<User> getUserByLoginAndPassword(String username, String password) {
        return null;
    }
}
