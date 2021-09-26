package com.generactive.repository;

import java.util.Optional;

public interface CRUD<T> {
    T create(T t);

    Optional<T> read(long id);

    Optional<T> update(T t);

    Optional<T> delete(long id);
}
