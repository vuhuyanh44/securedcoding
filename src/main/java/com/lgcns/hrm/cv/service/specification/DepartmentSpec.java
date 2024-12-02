package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.Department;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentSpec {
    public static Specification<Department> findByCondition(String searchName, String searchCode, String parentId) {
        return Specifications.<Department>and()
                .like(ObjectUtil.isNotEmpty(searchName),"name", "%" + searchName + "%")
                .like(ObjectUtil.isNotEmpty(searchCode),"code","%" + searchCode + "%")
                .eq(ObjectUtil.isNotEmpty(parentId),"id",parentId)
                .eq(ObjectUtil.isEmpty(searchCode) && ObjectUtil.isEmpty(searchName) && ObjectUtil.isEmpty(parentId),"parentId",null)
                .build();
    }
}
