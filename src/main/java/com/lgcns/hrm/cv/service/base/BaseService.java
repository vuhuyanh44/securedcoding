package com.lgcns.hrm.cv.service.base;

import com.lgcns.hrm.cv.common.definition.domain.Entity;
import com.lgcns.hrm.cv.exception.ResourceException;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import org.springframework.core.type.ClassMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public interface BaseService<E extends Entity, ID extends Serializable> {

    BaseRepository<E, ID> getRepository();


    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }


    default boolean existsById(ID id) {
        return getRepository().existsById(id);
    }

    default long count() {
        return getRepository().count();
    }


    default long count(Specification<E> specification) {
        return getRepository().count(specification);
    }


    default List<E> findAll() {
        return getRepository().findAll();
    }


    default List<E> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    default List<E> findAll(Specification<E> specification) {
        return getRepository().findAll(specification);
    }


    default List<E> findAll(Specification<E> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    default Page<E> findByPage(Pageable pageable) {
        return getRepository().findAll(pageable);
    }


    default Page<E> findByPage(int pageNumber, int pageSize) {
        return findByPage(PageRequest.of(pageNumber, pageSize));
    }


    default Page<E> findByPage(int pageNumber, int pageSize, Sort sort) {
        return findByPage(PageRequest.of(pageNumber, pageSize, sort));
    }


    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction, properties));
    }

    default Page<E> findByPage(Specification<E> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }


    default Page<E> findByPage(Specification<E> specification, int pageNumber, int pageSize) {
        return getRepository().findAll(specification, PageRequest.of(pageNumber, pageSize));
    }

    default Page<E> findByPage(int pageNumber, int pageSize, Sort.Direction direction) {
        return findByPage(PageRequest.of(pageNumber, pageSize, direction));
    }


    default void delete(E entity) {
        getRepository().delete(entity);
    }


    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }


    default void deleteAll(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }


    default void deleteAll() {
        getRepository().deleteAll();
    }


    default void deleteById(ID id) {
        getRepository().deleteById(id);
    }


    default E save(E domain) {
        return getRepository().save(domain);
    }


    default <S extends E> List<S> saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }


    default E saveAndFlush(E entity) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object object = field.get(entity);
            if (Objects.isNull(object)) {
                Field fieldCreateBy = clazz.getSuperclass().getDeclaredField("createBy");
                Field fieldCreateDate = clazz.getSuperclass().getDeclaredField("createTime");
                fieldCreateBy.setAccessible(true);
                fieldCreateDate.setAccessible(true);
                fieldCreateBy.set(entity, authentication.getName());
                fieldCreateDate.set(entity, new Date());
            } else {
                Field fieldUpdate = clazz.getSuperclass().getDeclaredField("updateBy");
                Field fieldUpdateDate = clazz.getSuperclass().getDeclaredField("updateTime");
                fieldUpdate.setAccessible(true);
                fieldUpdateDate.setAccessible(true);
                fieldUpdate.set(entity, authentication.getName());
                fieldUpdateDate.set(entity, new Date());
            }
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
        return getRepository().saveAndFlush(entity);
    }
    default List<E> saveAllAndFlush(List<E> entities) {
        return getRepository().saveAllAndFlush(entities);
    }


    default void flush() {
        getRepository().flush();
    }
}