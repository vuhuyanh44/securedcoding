package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.Department;
import com.lgcns.hrm.cv.model.vo.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepartmentTransform implements BaseTransform<Department, DepartmentVo> {
    @Override
    public Department toEntity(DepartmentVo model) {
        return BeanUtil.copy(model, Department.class);
    }

    @Override
    public DepartmentVo toModel(Department entity) {
        return BeanUtil.copy(entity, DepartmentVo.class);
    }
}
