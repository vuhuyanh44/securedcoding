package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.ScheduleJob;
import com.lgcns.hrm.cv.model.vo.ScheduleJobLogVo;
import com.lgcns.hrm.cv.model.vo.ScheduleJobVo;
import com.lgcns.hrm.cv.service.ScheduleJobLogService;
import com.lgcns.hrm.cv.service.ScheduleJobService;
import com.lgcns.hrm.cv.transform.ScheduleJobTransform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "ScheduleJob", description = "Schedule Job Management API")
@RestController
@RequestMapping("/scheduleJob")
@RequiredArgsConstructor
public class ScheduleController extends BaseController<ScheduleJob, ScheduleJobVo, String> {
    private final ScheduleJobService scheduleJobService;

    private final ScheduleJobLogService scheduleJobLogService;

    private final ScheduleJobTransform scheduleJobTransform;

    @Override
    public ScheduleJobService getWriteableService() {
        return scheduleJobService;
    }

    @Override
    public ScheduleJobTransform getBaseTransform() {
        return scheduleJobTransform;
    }

    @GetMapping("/{id}")
    public Result<ScheduleJobVo> getJobVOMapping(@PathVariable("id") String id) {
        var scheduleJobVo = scheduleJobService.getViewObject(id);
        return result(scheduleJobVo);
    }

    @Operation(
            summary = "create job", description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "", content = @Content(mediaType = "application/json"))}
    )
    @PostMapping("/")
    public Result<String> createJob(@Valid @RequestBody ScheduleJobVo scheduleJobVo) throws Exception{
        scheduleJobService.saveJob(scheduleJobVo);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<String> updateEntityMapping(@PathVariable("id") String id, @Valid @RequestBody ScheduleJobVo scheduleJobVo) throws Exception{
        scheduleJobVo.setId(id);
        scheduleJobService.updateJob(scheduleJobVo);
        return Result.success();
    }


    @PutMapping("/{id}/{action}")
    public Result<String> updateJobStateMapping(@PathVariable("id") String id, @PathVariable("action") String action) throws Exception{
        scheduleJobService.changeScheduleJobStatus(id, action);
        return Result.success();
    }


    @PutMapping("/executeOnce/{id}")
    public Result<String> executeOnce(@PathVariable("id") String id) {
        scheduleJobService.executeOnceJob(id);
        return Result.success();
    }

    @GetMapping("/allJobsInBean")
    public Result<Object> getAllJobs() {
        return result(scheduleJobService.getAllJobs());
    }

    @GetMapping("/log/list")
    public Result<Map<String, Object>> getJobLogVOListMapping(@RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                                              @RequestParam(defaultValue = "0", required = false) Integer pageNum) {
        var logList = scheduleJobLogService.getViewObjectList(pageSize, pageNum);
        return result(logList);
    }

    @GetMapping("/log/{id}")
    public Result<ScheduleJobLogVo> getJobLogVOMapping(@PathVariable("id") String id) {
        var jobLog = scheduleJobLogService.getViewObject(id);
        return result(jobLog);
    }

    @GetMapping("/schedule-job")
    public Result<Map<String, Object>> getScheduleJobs(
            @RequestParam(required = false) Long logId,
            @RequestParam(required = false) String jobName,
            @RequestParam(required = false) String jobKey,
            @RequestParam(required = false) String jobStatus,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", required = false) Integer pageNum) {
        var scheduleJobs = scheduleJobService.getByCondition(logId, jobName, jobKey, jobStatus, pageNum,pageSize);
        return result(scheduleJobs);
    }


}
