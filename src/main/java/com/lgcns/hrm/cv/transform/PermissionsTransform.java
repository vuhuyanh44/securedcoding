package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.Permission;
import com.lgcns.hrm.cv.entity.ScheduleJobLog;
import com.lgcns.hrm.cv.model.vo.PermissionVo;
import com.lgcns.hrm.cv.model.vo.ScheduleJobLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PermissionsTransform implements BaseTransform<Permission, PermissionVo>  {
    @Override
    public Permission toEntity(PermissionVo model) {
        return BeanUtil.copy(model, Permission.class);
    }

    @Override
    public PermissionVo toModel(Permission entity) {
        return BeanUtil.copy(entity, PermissionVo.class);
    }
}
