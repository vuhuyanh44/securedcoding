package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.SysParam;
import com.lgcns.hrm.cv.model.vo.SysParamVo;
import com.lgcns.hrm.cv.service.SysParamService;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import com.lgcns.hrm.cv.transform.SysParamTransform;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "SysParam", description = "Sys Param Management API")
@RestController
@RequestMapping("/sys-param")
@RequiredArgsConstructor
public class SysParamController extends BaseController<SysParam, SysParamVo, String> {

    private final SysParamTransform sysParamTransform;
    private final SysParamService sysParamService;

    @Override
    public BaseTransform<SysParam, SysParamVo> getBaseTransform() {
        return sysParamTransform;
    }

    @Override
    public BaseService<SysParam, String> getWriteableService() {
        return sysParamService;
    }

    @PostMapping("")
    public Result<String> save(@Valid @RequestBody SysParamVo typeVo) throws Exception{
        sysParamService.saveOrUpdate(typeVo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SysParam> getById(@PathVariable("id") String id) {
        return result(sysParamService.getById(id));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteById(@PathVariable("id") String id) {
        try {
            sysParamService.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getSysParams(
        @RequestParam(defaultValue = "", required = false) String searchType,
        @RequestParam(defaultValue = "", required = false) String searchCode,
        @RequestParam(defaultValue = "5", required = false) Integer pageSize,
        @RequestParam(defaultValue = "0", required = false) Integer pageNum
    ) {
        var sysParams = sysParamService.getSysParams(searchType, searchCode, pageNum, pageSize);
        return result(sysParams);
    }

    @GetMapping("/list-all")
    public Result<List<SysParam>> getAllSysParam() {
        return result(sysParamService.getAll());
    }

    @GetMapping("type/{type}")
    public Result<List<SysParam>> getListByCode(@PathVariable("type") String type) {
        return result(sysParamService.getLstByType(type));
    }
}
