package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.constants.ErrorCodes;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.controller.base.BaseController;
import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.model.vo.CandidateInfoVo;
import com.lgcns.hrm.cv.model.vo.CandidateVo;
import com.lgcns.hrm.cv.model.vo.ListEmailVo;
import com.lgcns.hrm.cv.service.CandidatesService;
import com.lgcns.hrm.cv.service.ExportExcelService;
import com.lgcns.hrm.cv.service.base.BaseService;
import com.lgcns.hrm.cv.transform.BaseTransform;
import com.lgcns.hrm.cv.transform.CandidateTranform;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "Candidates", description = "Candidates Management API")
@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
 public class CandidateController extends BaseController<Candidates, CandidateVo, Integer> {

    private final CandidateTranform candidateTranform;

    private final CandidatesService candidatesService;

    @Override
    public BaseTransform<Candidates, CandidateVo> getBaseTransform() {
        return candidateTranform;
    }

    @Override
    public BaseService<Candidates, Integer> getWriteableService() {
        return candidatesService;
    }

    @PostMapping("")
    public Result<Candidates> save(@Valid @RequestBody CandidateVo candidateVo) throws Exception {
        return Result.success(ErrorCodes.OK.getMessage(), candidatesService.saveOrUpdate(candidateVo));
    }

    @PostMapping("/upload-cv")
    public Result<CandidatesCv> upload(@RequestParam("file") MultipartFile file) throws Exception {
        CandidatesCv candidatesCv = candidatesService.upload(file);
        return result(candidatesCv);
    }

    @GetMapping("/search")
    public Result<Map<String, Object>> search(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String startDateReview,
            @RequestParam(required = false) String endDateReview,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String applyPosition,
            @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(defaultValue = "0", required = false) int pageNum
    ) throws Exception {
        var candidates = candidatesService.search(startDate, endDate, status, type, name, applyPosition, startDateReview, endDateReview, pageNum, pageSize);
        return result(candidates);
    }

    @GetMapping("/download")
    public ResponseEntity<Object> download(@RequestParam(name = "candidatesCvId", required = true) String candidatesCvId) throws Exception {
        InputStream inputStream = candidatesService.download(candidatesCvId);
        if (inputStream == null) {
            return null;
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(IOUtils.toByteArray(inputStream));
    }

    private final ExportExcelService exportExcelService;

//    @GetMapping("/exportExcel")
//    public void exportExcel(@RequestParam(required = false) String startDate,
//                            @RequestParam(required = false) String endDate,
//                            @RequestParam(required = false) String startDateReview,
//                            @RequestParam(required = false) String endDateReview,
//                            @RequestParam(required = false) String status,
//                            @RequestParam(required = false) String type,
//                            @RequestParam(required = false) String name,
//                            @RequestParam(required = false) String applyPosition,
//                            HttpServletResponse response) {
//        List<Candidates> candidates = candidatesService.getList(startDate, endDate, status, type, name, applyPosition, startDateReview, endDateReview);
//        candidates.forEach(item -> {
//            item.setLinkCv("http://10.124.18.61/cv/" + item.getId());
//            item.setOfferDateStr(item.getOfferDateStr());
//            item.setInterviewDateStr(item.getInterviewDateStr());
//            item.setReceiveCvDateStr(item.getReceiveCvDateStr());
//            item.setCheckCvDateStr(item.getCheckCvDateStr());
//            item.setProcessDateFromReceiveToCheck(candidatesService.dayBetween(item.getReceiveCvDate(), item.getCheckCvDate()));
//            item.setProcessDateFromCheckToOffer(candidatesService.dayBetween(item.getCheckCvDate(), item.getOfferDate()));
//        });
//        Map<String, Object> map = new HashMap<>();
//        map.put("reports", candidates);
//        exportExcelService.exportExcel("bao_cao_ung_vien.xlsx", map, response);
//    }

    @GetMapping("/{id}")
    public Result<Candidates> getCandidateById(@PathVariable("id") Integer id) {
        return result(candidatesService.getCandidateById(id));
    }

    @PutMapping("/update-stauts")
    public Result<Candidates> updateStatus(@Valid @RequestBody CandidateVo candidateVo) throws Exception{
        return result(candidatesService.updateStatus(candidateVo));
    }

    @PostMapping("/emails")
    public List<CandidateInfoVo> getCandidatesByEmails(@RequestBody ListEmailVo emails) {
        return candidatesService.getCandidatesByEmails(emails);
    }
}
