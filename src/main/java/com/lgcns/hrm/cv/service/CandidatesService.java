package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.utils.DateUtil;
import com.lgcns.hrm.cv.common.utils.StringUtil;
import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.entity.CandidatesCv;
import com.lgcns.hrm.cv.exception.ResourceException;
import com.lgcns.hrm.cv.model.vo.CandidateInfoVo;
import com.lgcns.hrm.cv.model.vo.CandidateVo;
import com.lgcns.hrm.cv.model.vo.ListEmailVo;
import com.lgcns.hrm.cv.repository.CandidateRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.CandidatesSpec;
import com.lgcns.hrm.cv.transform.CandidateTranform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidatesService extends AbstractBaseService<Candidates, Integer> {
    private final CandidateRepository candidateRepository;

    private final CandidateCvService candidateCvService;
    private final CandidateTranform candidateTranform;
    private final SysParamService sysParamService;

    @Override
    public BaseRepository<Candidates, Integer> getRepository() {
        return candidateRepository;
    }

    public Candidates saveOrUpdate(CandidateVo candidateVo) throws Exception {
        Candidates candidates = candidateTranform.toEntity(candidateVo);
//        if (!Objects.isNull(candidateVo.getId()) && !Objects.isNull(candidates.getStatus())) {
//            candidates.setCheckCvDate(LocalDateTime.now()); // Move to CandidatesCv
//        } else {
//            candidates.setReceiveCvDate(LocalDateTime.now()); // Move to CandidatesCv
            if (checkDuplicate(candidates.getEmail().trim())) {
                candidates.setStatus(sysParamService.getParamByTypeAndCode("CV_STATUS", "DUPLICATE"));
            }
//        }
        return super.saveAndFlush(candidates);
    }

    private boolean checkDuplicate(String email) {
        var timePoint = LocalDate.now().minusMonths(6);
        var candidate = StringUtil.isBlank(email) ? Optional.empty() :
                findCandidateByEmailAndTimePoint(email, timePoint);
        return candidate.isPresent();
    }

    public CandidatesCv upload(MultipartFile file) throws Exception {
        return candidateCvService.SaveWithFile(file);
    }

    public Page<Candidates> search(String startDate, String endDate, String status, String type, String name, String applyPosition, String startDateReview, String endDateReview, int pageNum, int pageSize) {

        return this.findByPage(CandidatesSpec.findByCondition(startDate, endDate, status, type, name, applyPosition, startDateReview, endDateReview), PageRequest.of(pageNum, pageSize));
    }

    public InputStream download(String candidatesCvId) throws Exception {
        return candidateCvService.download(candidatesCvId);
    }

    public List<Candidates> getList(String startDate, String endDate, String status, String type, String name, String applyPosition, String startDateReview, String endDateReview) {
        return candidateRepository.findAll(CandidatesSpec.findByCondition(startDate, endDate, status, type, name, applyPosition, startDateReview, endDateReview));
    }

    public Candidates getCandidateById(Integer id) {
        return candidateRepository.findById(id).orElse(new Candidates());
    }

    public Candidates updateStatus(CandidateVo candidateVo) throws Exception {
        if (Objects.isNull(candidateVo.getId()))
            throw new ResourceException("id not empty");
        Candidates candidates = candidateRepository.findById(candidateVo.getId()).orElse(null);
        if (candidates == null)
            throw new ResourceException("candidates doesn't exist");

        candidates.setStatus(candidateVo.getStatus());
        candidates.setOfferDate(candidateVo.getOfferDate());
        return candidateRepository.saveAndFlush(candidates);
    }

    public Optional<Candidates> findCandidateByEmailAndTimePoint(String email, LocalDate lastDateApply) {
        return candidateRepository.findAll(CandidatesSpec.findByEmailAndLastApply(email, lastDateApply)).stream().findFirst();
    }

    public String dayBetween(Temporal from, Temporal to) {
        if (from == null || to == null) {
            return "";
        }
        LocalDate dateFrom = (from instanceof LocalDateTime) ? LocalDate.from(from) : (LocalDate) from;
        LocalDate dateTo = (to instanceof LocalDateTime) ? LocalDate.from(to) : (LocalDate) to;
        long dayBetween = ChronoUnit.DAYS.between(dateFrom, dateTo);
        return String.valueOf(dayBetween);
    }

    public List<CandidateInfoVo> getCandidatesByEmails(ListEmailVo emails) {
        List<String> emailsList = emails.getEmails();
        List<CandidateInfoVo> candidates = new ArrayList<>();
        for (String email : emailsList) {
            Candidates candidate = candidateRepository.findAll(CandidatesSpec.findByEmailsAndHasOfferDate(email))
                    .stream().findFirst().orElse(null);
            if (candidate != null) {
                CandidateInfoVo candidateInfoVo = getCandidateInfoVo(candidate);
                candidates.add(candidateInfoVo);
            }
        }
        return candidates;
    }

    private static @NotNull CandidateInfoVo getCandidateInfoVo(Candidates candidate) {
        CandidateInfoVo candidateInfoVo = new CandidateInfoVo();
//        LocalDateTime interviewDate = (candidate.getCandidatesStatuses() == null || candidate.getCandidatesStatuses().isEmpty()) ? null :
//                candidate.getCandidatesStatuses().get(candidate.getCandidatesStatuses().size() - 1).getDateOfInterview();
//        candidateInfoVo.setPersonalEmail(candidate.getEmail());
//        candidateInfoVo.setCvSource(candidate.getCandidatesCvId().getFilePath());
//        candidateInfoVo.setInterviewDate(interviewDate != null ? DateUtil.format(interviewDate.toLocalDate(), DateUtil.PATTERN_DATE_ORIGINAL) : null);
//        candidateInfoVo.setCvReceivedDate(candidate.getReceiveCvDate() != null ? DateUtil.format(candidate.getReceiveCvDate().toLocalDate(), DateUtil.PATTERN_DATE_ORIGINAL) : null);
//        candidateInfoVo.setOfferDate(DateUtil.format(candidate.getOfferDate(), DateUtil.PATTERN_DATE_ORIGINAL));
        return candidateInfoVo;
    }
}


