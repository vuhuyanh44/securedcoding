package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GroupNPermissionsVo extends AbstractVo {
    GroupsVo groupsVo;
    List<PermissionVo> permissionVos;
}
