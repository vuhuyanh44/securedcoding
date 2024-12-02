package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.model.vo.PermissionVo;
import com.lgcns.hrm.cv.model.vo.GroupPermissionVo;
import com.lgcns.hrm.cv.service.PermissionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permissions", description = "Permissions Management API")
@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionsController {
    private final PermissionsService service;

    @GetMapping
    public Result<List<PermissionVo>> getPermissions() {
        return Result.success("Get Permissions Success", service.getPermissions());
    }

    @PostMapping("/set-for-a-group")
    public Result<GroupPermissionVo> setPermissionForGroup(
            @RequestBody GroupPermissionVo request
    ) {
        return Result.success("Set Permission for Group Success",service.setPermissionForGroupId(request));
    }

    @PostMapping("/create")
    public Result<PermissionVo> createPermission(
            @RequestBody PermissionVo request
    ) {
        return Result.success("Create Permission Success", service.createPermission(request));
    }

    @PutMapping("/update")
    public Result<PermissionVo> updatePermission(
            @RequestBody PermissionVo request
    ) {
        return Result.success("Update Permission Success", service.updatePermission(request));
    }
}
