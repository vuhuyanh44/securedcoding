package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.exception.PlatformException;
import com.lgcns.hrm.cv.entity.Permission;
import com.lgcns.hrm.cv.model.vo.PermissionVo;
import com.lgcns.hrm.cv.model.vo.GroupPermissionVo;
import com.lgcns.hrm.cv.repository.PermissionRepository;
import com.lgcns.hrm.cv.repository.GroupRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.transform.PermissionsTransform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionsService extends AbstractBaseService<Permission, Integer> {
    private final PermissionRepository permissionRepository;

    private final GroupRepository groupRepository;

    private final PermissionsTransform permissionsTransform;
    
    @Override
    public PermissionRepository getRepository() {
        return permissionRepository;
    }

    public List<PermissionVo> getPermissions() {
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(el -> new PermissionVo(el.getId(), el.getComponentName(), el.getAction())).toList();
    }

    public GroupPermissionVo setPermissionForGroupId(GroupPermissionVo request) {
        var permission = permissionRepository.findById(request.getPermission_id())
                .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Permission doesn't exist"));
        var group = groupRepository.findById(request.getGroup_id())
                .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Group doesn't exist"));

        group.getPermissions().add(permission);
        groupRepository.saveAndFlush(group);

        return new GroupPermissionVo(request.getPermission_id(), request.getGroup_id());
    }

    public PermissionVo createPermission(PermissionVo request) {
        var permission = permissionRepository.findByComponentNameAndAction(request.getComponentName(), request.getAction());

        if (permission.isPresent()) {
            throw new PlatformException(ErrorCodes.INVALID_REQUEST, "Permission is existed");
        }

        var newPermission = permissionRepository.save(new Permission()
                .setComponentName(request.getComponentName()).setAction(request.getAction()));

        return permissionsTransform.toModel(newPermission);
    }

    public PermissionVo updatePermission(PermissionVo request) {
        var permission = permissionRepository.findById(request.getId())
                .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Permission doesn't exist"));
        permission.setComponentName(request.getComponentName()).setAction(request.getAction());

        var newPermission = permissionRepository.save(permission);

        return permissionsTransform.toModel(newPermission);
    }
}
