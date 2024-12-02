package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.Department;
import com.lgcns.hrm.cv.model.vo.DepartmentVo;
import com.lgcns.hrm.cv.repository.DepartmentRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.DepartmentSpec;
import com.lgcns.hrm.cv.transform.DepartmentTransform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService extends AbstractBaseService<Department, Integer> {

    private final DepartmentRepository departmentRepository;
    private final DepartmentTransform departmentTransform;


    @Override
    public BaseRepository<Department, Integer> getRepository() {
        return departmentRepository;
    }

    @Transactional
    public void saveOrUpdate(DepartmentVo typeVo) throws Exception {
        var department = departmentTransform.toEntity(typeVo);
        if (ObjectUtil.isEmpty(department.getCode())) {
            throw new IllegalArgumentException("Department code is required");
        }
        if (ObjectUtil.isEmpty(department.getName())) {
            throw new IllegalArgumentException("Department name is required");
        }
        var departmentParent = ObjectUtil.isNull(department.getParentId()) ? null :
                this.findById(department.getParentId());
        if (ObjectUtil.isNotNull(departmentParent)) {
            department.setParent(departmentParent);
        }
        super.saveAndFlush(department);
    }

    public Department getById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department getbyCode(String code) {
        return departmentRepository.findByCode((code));
    }

    @Override
    public void deleteById(Integer id) {
        departmentRepository.deleteById(id);
    }

    public List<Department> getAll() {
        return departmentRepository.findAll().stream().toList();
    }

    public Page<Department> getDepartments(String parentId, String searchName, String searchCode, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Department> conditionQuery = DepartmentSpec.findByCondition(searchName, searchCode, parentId);
        return departmentRepository.findAll(conditionQuery, pageable);
    }
}
