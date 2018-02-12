package com.joanna.onlineclinic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.util.ReflectionTestUtils;

public abstract class AbstractRepositoryStub<T extends BaseEntity> implements JpaRepository<T, Long> {

    private AtomicLong idSequence = new AtomicLong(1);
    protected Map<Long, T> store = new HashMap<>();

    @Override
    public <S extends T> S save(S entity) {
        if(entity.getId() == 0) {
            ReflectionTestUtils.setField(entity, "id", idSequence.getAndIncrement());
        }

        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public T findOne(Long id) {
        return store.get(id);
    }

    @Override
    public boolean exists(Long id) {
        return store.containsKey(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<T> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> findAll(Iterable<Long> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        return store.size();
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void delete(T entity) {
        store.remove(entity.getId());
    }

    @Override
    public void delete(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteInBatch(Iterable<T> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        store.clear();
    }

    @Override
    public T getOne(Long id) {
        return store.get(id);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> S saveAndFlush(S s) {
        return save(s);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }
}
