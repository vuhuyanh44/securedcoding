package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.model.vo.DashboardCountInterviewerVo;
import com.lgcns.hrm.cv.model.vo.DashboardStatusAndResourceVo;

import java.util.List;
import java.util.Map;

/**
 * @author pigx
 */
public interface DashboardService {
    List<Map<String, Object>> getNumberOfCVSentByPartnersOverTime(String startTime, String endTime);

    List<Map<String, Object>> getPassFailRate(String startTime, String endTime);

    List<Map<String, Object>> countNumberOfCVAccordingToFailedPass(String startTime, String endTime);

    List<DashboardStatusAndResourceVo> getStatusAndResourceCv(String startTime, String endTime);

    List<DashboardCountInterviewerVo> countNumberInterviewsByInterviewer(String startTime, String endTime);
}
