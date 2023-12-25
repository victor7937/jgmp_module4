package com.epam.victor.repository;

import java.util.List;
import java.util.function.Predicate;

public interface PageableRepository<T> {
    List<T> findAllOfPage(int pageSize, int pageNum);

    List<T> findAllOfPageWithCondition(int pageSize, int pageNum, Predicate<T> predicate);

}
