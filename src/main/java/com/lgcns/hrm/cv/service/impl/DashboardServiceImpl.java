package com.lgcns.hrm.cv.service.impl;

import com.lgcns.hrm.cv.model.vo.DashboardCountInterviewerVo;
import com.lgcns.hrm.cv.model.vo.DashboardStatusAndResourceVo;
import com.lgcns.hrm.cv.repository.entitymanager.DashboardRepository;
import com.lgcns.hrm.cv.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pigx
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DashboardRepository dashboardRepository;

    @Override
    public List<Map<String, Object>> getNumberOfCVSentByPartnersOverTime(String startTime, String endTime) {
        var dashboardTypeVo = dashboardRepository.getNumberOfCVSentByPartnersOverTime(startTime, endTime);
        return dashboardTypeVo.stream()
                .map(m -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("label", m.getType());
                    entry.put("data", Arrays.stream(m.getTotal().split(",")).map(Integer::parseInt).toList());
                    return entry;
                }).toList();
    }

    @Override
    public List<Map<String, Object>> getPassFailRate(String startTime, String endTime) {
        var dashboardPassFailRateVos = dashboardRepository.getPassFailRate(startTime, endTime);
        return dashboardPassFailRateVos.stream()
                .map(m -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("label", m.getStatus());
                    entry.put("data", m.getTotal());
                    return entry;
                }).toList();
    }

    @Override
    public List<Map<String, Object>> countNumberOfCVAccordingToFailedPass(String startTime, String endTime) {
        var dashboardTypeVo = dashboardRepository.countNumberOfCVAccordingToFailedPass(startTime, endTime);
        return dashboardTypeVo.stream()
                .map(m -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("label", m.getMonth());
                    entry.put("data", Arrays.stream(m.getTotal().split(",")).map(Integer::parseInt).toList());
                    return entry;
                }).toList();
    }

    @Override
    public List<DashboardStatusAndResourceVo> getStatusAndResourceCv(String startTime, String endTime) {
        return dashboardRepository.getStatusAndResourceCv(startTime, endTime);
    }

    @Override
    public List<DashboardCountInterviewerVo> countNumberInterviewsByInterviewer(String startTime, String endTime){
        return dashboardRepository.countNumberInterviewsByInterviewer(startTime,endTime);
    }
}
