package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.common.utils.DateTimeUtil;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.model.vo.DashboardCountInterviewerVo;
import com.lgcns.hrm.cv.model.vo.DashboardStatusAndResourceVo;
import com.lgcns.hrm.cv.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author pigx
 */
@Tag(name = "Dashboard", description = "Dashboard Management API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private static final String PATTERN_DATE = "yyyy-MM-dd";

    @GetMapping("/statistical-by-type")
    public Result<List<Map<String, Object>>> statisticalByType(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        var localStartTime = convertDateTime(startTime);
        var localEndTime = convertDateTime(endTime);
        var data = dashboardService.getNumberOfCVSentByPartnersOverTime(localStartTime, localEndTime);
        return Result.success("Success!", data);
    }

    @GetMapping("/statistical-by-pass-fail-rate")
    public Result<List<Map<String, Object>>> statisticalByPassFailRate(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        var localStartTime = convertDateTime(startTime);
        var localEndTime = convertDateTime(endTime);
        var data = dashboardService.getPassFailRate(localStartTime, localEndTime);
        return Result.success("Success!", data);
    }

    @GetMapping("/statistical-by-count-pass-fail")
    public Result<List<Map<String, Object>>> statisticalByCountPassFail(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        var localStartTime = convertDateTime(startTime);
        var localEndTime = convertDateTime(endTime);
        var data = dashboardService.countNumberOfCVAccordingToFailedPass(localStartTime, localEndTime);
        return Result.success("Success!", data);
    }

    @GetMapping("/statistical-by-cv")
    public Result<List<DashboardStatusAndResourceVo>> statisticalByCv(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        var localStartTime = convertDateTime(startTime);
        var localEndTime = convertDateTime(endTime);
        var data = dashboardService.getStatusAndResourceCv(localStartTime, localEndTime);
        return Result.success("Success!", data);
    }

    @GetMapping("/statistical-by-interviewer")
    public Result<List<DashboardCountInterviewerVo>> statisticalByInterviewer(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        var localStartTime = convertDateTime(startTime);
        var localEndTime = convertDateTime(endTime);
        var data = dashboardService.countNumberInterviewsByInterviewer(localStartTime, localEndTime);
        return Result.success("Success!", data);
    }

    private String convertDateTime(String time) {
        return DateTimeUtil.format(
                LocalDate.from(DateTimeUtil.parse(time, DateUtil.PATTERN_DATE)).plusDays(1)
                , PATTERN_DATE);
    }
}
