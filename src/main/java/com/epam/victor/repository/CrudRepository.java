package com.epam.victor.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void create (T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity);

    boolean delete(Long id);

    boolean existsById(ID id);

    Integer count();
}
