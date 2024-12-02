package com.lgcns.hrm.cv.transform;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public interface BaseTransform<E, M> {

    E toEntity(M model);
    M toModel(E entity);
    default Page<M> mapEntityPageIntoDtoPage(Page<E> entities) {
        return entities.map(this::toModel);
    }

    default List<M> mapEntityListIntoDtoList(List<E> entities) {
        return entities.stream().map(this::toModel).toList();
    }
}
