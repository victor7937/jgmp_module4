package com.epam.victor.repository;

import java.util.List;

public interface PageableRepository<T> {
    List<T> findAllOfPage(int pageSize, int pageNum);

}
