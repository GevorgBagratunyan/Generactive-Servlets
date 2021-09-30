package com.generactive.repository;

import java.util.Optional;

public interface RepositoryCRUD<T> {
    T create(T t);

    Optional<T> read(Long id);

    void update(T t);

    void delete(Long id);
}
