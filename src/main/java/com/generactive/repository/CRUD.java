package com.generactive.repository;

import java.util.Optional;

public interface CRUD<T> {
    T create(T t);

    Optional<T> read(long ID);

    Optional<T> update(T t);

    Optional<T> delete(long ID);
}
