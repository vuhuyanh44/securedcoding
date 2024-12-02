package com.lgcns.hrm.cv.controller.base;

import com.lgcns.hrm.cv.common.definition.domain.Entity;
import com.lgcns.hrm.cv.common.domain.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Controller {

    default <E extends Entity> Result<E> result(E domain) {
        if (ObjectUtils.isNotEmpty(domain)) {
            return Result.content(domain);
        } else {
            return Result.empty();
        }
    }

    default <E extends Entity> Result<List<E>> result(List<E> domains) {

        if (null == domains) {
            return Result.failure("Query data failed!");
        }

        if (CollectionUtils.isNotEmpty(domains)) {
            return Result.success("Query data successfully!", domains);
        } else {
            return Result.empty("No data found!");
        }
    }


    default <E extends Entity> Result<Map<String, Object>> result(Page<E> pages) {
        if (null == pages) {
            return Result.failure("Query data failed!");
        }

        if (CollectionUtils.isNotEmpty(pages.getContent())) {
            return Result.success("Query data successfully!", getPageInfoMap(pages));
        } else {
            return Result.empty("No data found!");
        }
    }
    default Result<Map<String, Object>> result(Map<String, Object> map) {

        if (null == map) {
            return Result.failure("Query data failed!");
        }

        if (MapUtils.isNotEmpty(map)) {
            return Result.success("Query data successfully!", map);
        } else {
            return Result.empty("No data found!");
        }
    }

    default Result<Object> result(Object domain) {
        if (ObjectUtils.isNotEmpty(domain)) {
            return Result.content(domain);
        } else {
            return Result.empty();
        }
    }
    default <ID extends Serializable> Result<ID> result(ID parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }


    default Result<Boolean> result(boolean status) {
        if (status) {
            return Result.success("Operation successful!", status);
        } else {
            return Result.failure("Operation failed!", status);
        }
    }

    default <E extends Entity> Map<String, Object> getPageInfoMap(Page<E> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }

    default <E extends Entity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages, long totalElements) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }
}
