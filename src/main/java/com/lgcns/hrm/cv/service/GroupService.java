package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.exception.PlatformException;
import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.model.vo.PermissionVo;
import com.lgcns.hrm.cv.model.vo.GroupNPermissionsVo;
import com.lgcns.hrm.cv.model.vo.GroupsVo;
import com.lgcns.hrm.cv.repository.UserRepository;
import com.lgcns.hrm.cv.repository.GroupRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.transform.PermissionsTransform;
import com.lgcns.hrm.cv.transform.GroupsTransform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author pigx
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService extends AbstractBaseService<Group, Integer> {
    private final GroupRepository groupRepository;

    private final GroupsTransform groupsTransform;

    private final PermissionsTransform permissionsTransform;
    @Override
    public GroupRepository getRepository() {
        return groupRepository;
    }

    public final UserRepository userRepository;

    public GroupsVo createGroup(GroupsVo request) {
        var group = groupRepository.findByName(request.getName());

        if (!Objects.isNull(group)) {
            throw new PlatformException(ErrorCodes.INVALID_REQUEST, "Group is existed");
        }

        var newGroup = groupRepository.save(new Group()
                .setName(request.getName()));

        return groupsTransform.toModel(newGroup);
    }

    public List<GroupsVo> getGroups() {
        var groups = groupRepository.findAll();

        return groups.stream().map(el -> new GroupsVo(el.getId(), el.getName())).toList();
    }

    public List<GroupNPermissionsVo> getGroupsNPermissions() {
        var groups = groupRepository.findAll();

        return groups.stream().map(x-> {
            List<PermissionVo> permissionsByGroupId = x.getPermissions()
                    .stream().map(y->new PermissionVo().setComponentName(y.getComponentName()).setAction(y.getAction()).setId(y.getId())).toList();
            var group = new GroupsVo()
                    .setId(x.getId())
                    .setName(x.getName());
            return new GroupNPermissionsVo()
                    .setGroupsVo(group)
                    .setPermissionVos(permissionsByGroupId);
        }).toList();
    }

    public List<GroupNPermissionsVo> getPermissionByGroupId(Integer id) {
        var groups = groupRepository.findById(id);

        return groups.stream().map(x-> {
            List<PermissionVo> permissionsByGroupId = x.getPermissions()
                    .stream().map(y->new PermissionVo().setComponentName(y.getComponentName()).setAction(y.getAction())).toList();
            var group = groupsTransform.toModel(x);
            return new GroupNPermissionsVo()
                    .setGroupsVo(group)
                    .setPermissionVos(permissionsByGroupId);
        }).toList();
    }

    public GroupsVo updateGroup(GroupsVo request) {
        var group = groupRepository.findById(request.getId())
                .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Group doesn't exist"));

        group.setName(request.getName());

        var newGroup = groupRepository.save(group);

        return groupsTransform.toModel(newGroup);
    }

    public GroupNPermissionsVo updatePermissionByGroupId(List<PermissionVo> request, Integer id) {
        var group = groupRepository.findById(id)
                .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Group doesn't exist"));

        group.getPermissions().clear();
        group.getPermissions().addAll(request.stream()
                .map(permissionsTransform::toEntity).toList());

        var newGroup = groupRepository.save(group);

        List<PermissionVo> permissionsByGroupId = newGroup.getPermissions()
                .stream().map(y->new PermissionVo().setComponentName(y.getComponentName()).setAction(y.getAction()).setId(y.getId())).toList();

        var groupVo = groupsTransform.toModel(newGroup);
        return new GroupNPermissionsVo()
                .setGroupsVo(groupVo)
                .setPermissionVos(permissionsByGroupId);
    }

    @Transactional
    public void deleteGroup(Integer id) {
        try {
            var group = groupRepository.findById(id)
                    .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "Group doesn't exist"));

            groupRepository.delete(group);
        } catch (Exception e) {
            throw new PlatformException(ErrorCodes.INVALID_REQUEST);
        }
    }

    public void assignGroupsForUserId(List<Integer> groupIds, Integer userId) {
        try {
            var groups = groupRepository.findAllById(groupIds.stream().toList());
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new PlatformException(ErrorCodes.INVALID_REQUEST, "User Id doesn't exist"));

            user.getGroups().clear();
            user.getGroups().addAll(groups);

            userRepository.save(user);
        } catch (Exception e) {
            throw new PlatformException(ErrorCodes.INVALID_REQUEST);
        }
    }
}
