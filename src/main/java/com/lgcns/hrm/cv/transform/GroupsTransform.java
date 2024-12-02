package com.lgcns.hrm.cv.transform;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.model.vo.GroupsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GroupsTransform implements BaseTransform<Group, GroupsVo> {
    @Override
    public Group toEntity(GroupsVo model) {
        return BeanUtil.copy(model, Group.class);
    }

    @Override
    public GroupsVo toModel(Group entity) {
        return BeanUtil.copy(entity, GroupsVo.class);
    }
}
