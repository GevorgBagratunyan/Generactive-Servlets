package com.generactive.storage;

import com.generactive.model.User;
import com.generactive.model.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CRUD<User> {

    //Temporary using List instead of DB
    private final static List<User> USERS = new ArrayList<>();

    public UserRepository() {
        USERS.add(new User(1, "Gev", "admin", Role.ADMIN));
        USERS.add(new User(2, "User", "user", Role.USER));
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return USERS.stream()
                .filter(user -> user.getUsername().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    public boolean userIsExist(String login, String password) {
        return USERS.stream()
                .anyMatch(user -> user.getUsername().equals(login) && user.getPassword().equals(password));
    }

    @Override
    public User create(User user) {
        USERS.add(user);
        return USERS.get(user.getId());
    }

    @Override
    public Optional<User> read(int ID) {
        return USERS.stream()
                .filter(user -> user.getId() == ID)
                .findFirst();
    }

    @Override
    public Optional<User> update(int ID, User user) {
        USERS.set(ID, user);
        return USERS.stream()
                .filter(u -> u.getId() == ID)
                .findFirst();
    }

    @Override
    public Integer delete(int ID) {
        Optional<User> user = USERS.stream()
                .filter(u -> u.getId() == ID)
                .findFirst();
        if (user.isPresent()) {
            USERS.remove(ID);
        }
        return 0;
    }
}
