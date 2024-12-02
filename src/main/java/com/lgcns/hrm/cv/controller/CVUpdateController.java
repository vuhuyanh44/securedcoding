package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.service.CandidateCvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/cv-update")
@RequiredArgsConstructor
public class CVUpdateController {
    private final CandidateCvService candidateCvService;
    @PutMapping
    public Result<String> updateCV(@RequestParam("startDate") String start, @RequestParam("endDate") String end) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_DATE_ORIGINAL);
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            candidateCvService.updateMissingCV(startDate, endDate);
            return Result.success();
        }
        catch (Exception e){
            return Result.failure(e.getMessage());
        }
    }
}
