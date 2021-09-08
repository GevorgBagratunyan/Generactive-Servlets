package com.generactive.storage;

import java.util.Optional;

public interface CRUD<T> {
    T create(T t);

    Optional<T> read(int ID);

    Optional<T> update(int ID, T t);

    Integer delete(int ID);
}
