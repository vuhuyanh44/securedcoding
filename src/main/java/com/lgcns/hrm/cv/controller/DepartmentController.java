package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.Department;
import com.lgcns.hrm.cv.model.vo.DepartmentVo;
import com.lgcns.hrm.cv.service.DepartmentService;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import com.lgcns.hrm.cv.transform.DepartmentTransform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pigx
 */
@Slf4j
@Tag(name = "Department", description = "Department Management API")
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController extends BaseController<Department, DepartmentVo, Integer> {

    private final DepartmentTransform departmentTransform;
    private final DepartmentService departmentService;

    @Override
    public BaseTransform<Department, DepartmentVo> getBaseTransform() {
        return departmentTransform;
    }

    @Override
    public BaseService<Department, Integer> getWriteableService() {
        return departmentService;
    }

    @Operation(summary = "Create/Update Department. id != null/empty => update, id = null/empty  => create")
    @PostMapping("")
    public Result<String> save(@Valid @RequestBody DepartmentVo typeVo) throws Exception {
        try {
            departmentService.saveOrUpdate(typeVo);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    @Operation(summary = "Get department by id")
    @GetMapping("/{id}")
    public Result<Department> getById(@PathVariable("id") Integer id) {
        return result(departmentService.getById(id));
    }

    @Operation(summary = "Delete department by id")
    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable("id") Integer id) {
        try {
            departmentService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @Operation(summary = "Get list departments", description = "Returns list departments")
    @GetMapping("/list")
    public Result<Page<Department>> getDepartments(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String searchCode,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer pageNum
    ) {
        var departments = departmentService.getDepartments(parentId, searchName, searchCode, pageNum, pageSize);
        return Result.success(ErrorCodes.OK.getMessage(), departments);
    }

    @Operation(summary = "Get list all departments")
    @GetMapping("/list-all")
    public Result<List<Department>> getAllDepartment() {
        return result(departmentService.getAll());
    }
}
