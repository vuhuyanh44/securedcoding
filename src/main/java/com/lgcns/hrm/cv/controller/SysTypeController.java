package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.SysType;
import com.lgcns.hrm.cv.model.vo.SysTypeVo;
import com.lgcns.hrm.cv.model.enums.ScheduleStatus;
import com.lgcns.hrm.cv.service.SysTypeService;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import com.lgcns.hrm.cv.transform.SysTypeTransform;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "SysType", description = "Sys Type Management API")
@RestController
@RequestMapping("/sys-type")
@RequiredArgsConstructor
public class SysTypeController extends BaseController<SysType, SysTypeVo, Integer> {

    private final SysTypeTransform sysTypeTransform;
    private final SysTypeService sysTypeService;

    @Override
    public BaseTransform<SysType, SysTypeVo> getBaseTransform() {
        return sysTypeTransform;
    }

    @Override
    public BaseService<SysType, Integer> getWriteableService() {
        return sysTypeService;
    }

    @PostMapping("")
    public Result<String> save(@Valid @RequestBody SysTypeVo typeVo) throws Exception{
        sysTypeService.saveOrUpdate(typeVo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SysType> getById(@PathVariable("id") Integer id) {
        return result(sysTypeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable("id") Integer id) {
        try {
            sysTypeService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getSysTypes(
            @RequestParam(defaultValue = "", required = false) String searchName,
            @RequestParam(defaultValue = "", required = false) String searchCode,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer pageNum
    ) {
        var sysTypes = sysTypeService.getSysTypes(searchName, searchCode, pageNum, pageSize);
        return result(sysTypes);
    }

    @GetMapping("/list-all")
    public Result<List<SysType>> getAllSysType() {
        return result(sysTypeService.getAll());
    }

}
