package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.model.vo.UserSearchVo;
import com.lgcns.hrm.cv.model.vo.UserVo;
import com.lgcns.hrm.cv.service.UserService;
import com.lgcns.hrm.cv.service.ExportExcelService;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import com.lgcns.hrm.cv.transform.UserTransform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "User", description = "User Management API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController<User, UserVo, Integer> {

    private final UserTransform userTransform;
    private final UserService userService;
    private final ExportExcelService exportExcelService;

    @Override
    public BaseTransform<User, UserVo> getBaseTransform() {
        return userTransform;
    }

    @Override
    public BaseService<User, Integer> getWriteableService() {
        return userService;
    }

    @Operation(summary = "Create/Update User. id != null/empty => update, id = null/empty  => create")
    @PostMapping
    public Result<String> save(@Valid @RequestBody UserVo typeVo) throws Exception {
        try {
            userService.saveOrUpdate(typeVo);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "Upload Users")
    @PostMapping("/import-excel")
    public Result<String> uploadExcel(@RequestParam("file") MultipartFile excelFile) {
        try {
            userService.importExcel(excelFile);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "export excel sample")
    @GetMapping("/export-excel-sample")
    public void exportExcel(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        exportExcelService.exportExcel("employee-list-sample.xlsx", map, response);
    }


    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable("id") Integer id) {
        return result(userService.getById(id));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable("id") Integer id) {
        try {
            userService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @Operation(summary = "Get list users", description = "Returns list users")
    @GetMapping("/list")
    public Result<Page<UserVo>> getUsers(
            UserSearchVo userSearchParam,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer pageNum
    ) {
        var users = userService.getUsers(userSearchParam, pageNum, pageSize);
        return Result.success(ErrorCodes.OK.getMessage(), users);
    }

    @Operation(summary = "Get list all users")
    @GetMapping("/list-all")
    public Result<List<User>> getAllUser() {
        return result(userService.getAll());
    }

}
