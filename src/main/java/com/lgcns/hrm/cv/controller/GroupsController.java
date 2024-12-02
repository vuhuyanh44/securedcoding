package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.model.vo.PermissionVo;
import com.lgcns.hrm.cv.model.vo.GroupNPermissionsVo;
import com.lgcns.hrm.cv.model.vo.GroupsVo;
import com.lgcns.hrm.cv.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Groups", description = "Groups Management API")
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {
    private final GroupService service;

    @PostMapping
    public Result<GroupsVo> create(
            @RequestBody GroupsVo request
    ) {
        return Result.success("Create Group Success", service.createGroup(request));
    }

    @GetMapping("")
    public Result<List<GroupsVo>> getGroups() {
        return Result.success("Get Groups Success", service.getGroups());
    }

    @GetMapping("/permissions")
    public Result<List<GroupNPermissionsVo>> getGroupsNPermissions() {
        return Result.success("Get Groups And Permissions Success", service.getGroupsNPermissions());
    }

    @GetMapping("/permissions/{id}")
    public Result<List<GroupNPermissionsVo>> getPermissionsByGroupId(@PathVariable("id") Integer id) {
        return Result.success(
                "Get Groups And Permissions Success",
                service.getPermissionByGroupId(id)
        );
    }

    @PutMapping("/permissions/{id}")
    public Result<GroupNPermissionsVo> updatePermissionByGroupId(
            @RequestBody List<PermissionVo> request,
            @PathVariable("id") Integer id
    ) {
        return Result.success(
                "Update Permissions By A Group Id Success",
                service.updatePermissionByGroupId(request, id)
        );
    }

    @PostMapping("/assign/{user_id}")
    public Result<GroupsVo> assignGroupForUserId(
            @RequestBody List<Integer> groupIds,
            @PathVariable("user_id") Integer userId
    ) {
        service.assignGroupsForUserId(groupIds, userId);

        return Result.success("Assign Groups Success");
    }

    @PutMapping("")
    public Result<GroupsVo> update(
            @RequestBody GroupsVo request
    ) {
        return Result.success("Update Group Success", service.updateGroup(request));
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Integer id) {
        service.deleteGroup(id);

        return Result.success("Delete Group Success");
    }
}
