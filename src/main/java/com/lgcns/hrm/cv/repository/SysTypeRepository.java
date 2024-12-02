package com.lgcns.hrm.cv.repository;

import com.lgcns.hrm.cv.entity.SysType;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SysTypeRepository extends BaseRepository<SysType, Integer> {
    public SysType findByCode(String code);

    @Query("SELECT id FROM SysType WHERE code = :code")
    Optional<Integer> findIdByCode(String code);
}
