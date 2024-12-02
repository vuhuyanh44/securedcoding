package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.Department;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author pigx
 */
public interface DepartmentRepository extends BaseRepository<Department, Integer> {
    List<Department> findByParentIdIsNull(Pageable pageable);

    Department findByCode(String code);
}
