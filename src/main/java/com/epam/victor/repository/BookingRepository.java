package com.epam.victor.repository;

import com.epam.victor.model.BookingEntity;
import com.epam.victor.repository.exception.NotFoundException;
import com.epam.victor.storage.ObjectStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class BookingRepository<T extends BookingEntity> implements CrudRepository<T, Long>, PageableRepository<T> {

    private final Class<T> entityType;

    protected final ObjectStorage objectStorage;

    public BookingRepository(Class<T> entityType, ObjectStorage objectStorage) {
        this.entityType = entityType;
        this.objectStorage = objectStorage;
    }

    @Override
    public void create(T entity) {
        findAll().add(entity);
    }

    @Override
    public Optional<T> findById(Long id) {
        return findAll().stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public List<T> findAll() {
        return (List<T>) objectStorage.getEntityList(entityType);
    }

    @Override
    public void update(T entity) {
        Long id = entity.getId();
        T currentEntity = findById(id)
                .orElseThrow(() -> new NotFoundException("Id "+ id +" not found"));
        List<T> entityList = findAll();
        entityList.set(entityList.indexOf(currentEntity), entity);
    }

    @Override
    public boolean delete(Long id) {
        if (!existsById(id)){
            return false;
        }
        T entityToDelete = findById(id).get();
        findAll().remove(entityToDelete);
        return true;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Integer count() {
        return findAll().size();
    }

    @Override
    public List<T> findAllOfPage(int pageSize, int pageNum) {
        List<T> entityList = findAll();
        return findPaged(pageSize, pageNum, entityList);
    }

    public List<T> findAllOfPageWithCondition(int pageSize, int pageNum, Predicate<T> predicate) {
        List<T> entityList = findAll().stream().filter(predicate).toList();
        return findPaged(pageSize, pageNum, entityList);

    }

    private List<T> findPaged (int pageSize, int pageNum, List<T> entityList){
        int count = entityList.size();
        int startIndex = pageNum * pageSize;
        if (startIndex >= count) {
            return new ArrayList<>();
        }
        else if ((startIndex + pageSize) >= count){
            return entityList.subList(startIndex, count);
        }
        else {
            return entityList.subList(startIndex, startIndex + pageSize);
        }
    }

    public Long newId (){
        return findAll().stream()
                .map(BookingEntity::getId)
                .max(Long::compare)
                .orElse(1L) + 1;
    }
}
