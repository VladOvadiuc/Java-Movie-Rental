package mpp.vlad_dani.server.repository;

import mpp.vlad_dani.common.domain.BaseEntity;
import mpp.vlad_dani.common.domain.validator.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class MemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public MemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        IllegalArgumentException exception=(id == null)?new IllegalArgumentException("id must not be null"):null;
        Optional.ofNullable(exception).ifPresent(e->{throw e;});
        //if (id == null) {
        //    throw new IllegalArgumentException("id must not be null");
        //}
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws Exception {
        IllegalArgumentException exception=(entity == null)?new IllegalArgumentException("id must not be null"):null;
        Optional.ofNullable(exception).ifPresent(e->{throw e;});
        //if (entity == null) {
        //    throw new IllegalArgumentException("id must not be null");
        //}
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        IllegalArgumentException exception=(id == null)?new IllegalArgumentException("id must not be null"):null;
        Optional.ofNullable(exception).ifPresent(e->{throw e;});
        //if (id == null) {
        //    throw new IllegalArgumentException("id must not be null");
        //}
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws Exception {
        IllegalArgumentException exception=(entity == null)?new IllegalArgumentException("id must not be null"):null;
        Optional.ofNullable(exception).ifPresent(e->{throw e;});
        //if (entity == null) {
        //    throw new IllegalArgumentException("movie must not be null");
        //}
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
