package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.Candidates;
import com.lgcns.hrm.cv.entity.CandidatesStatus;
import com.lgcns.hrm.cv.repository.base.BaseRepository;

import java.util.List;

public interface CandidatesStatusRepository extends BaseRepository<CandidatesStatus, String> {

    List<CandidatesStatus> findByCandidateId(Candidates candidates);
}
