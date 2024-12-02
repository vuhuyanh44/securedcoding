package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.SysParam;
import com.lgcns.hrm.cv.entity.SysType;
import com.lgcns.hrm.cv.model.vo.SysParamVo;
import com.lgcns.hrm.cv.repository.SysParamRepository;
import com.lgcns.hrm.cv.repository.SysTypeRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.SysParamSpec;
import com.lgcns.hrm.cv.transform.SysParamTransform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysParamService extends AbstractBaseService<SysParam, String> {

    private final SysParamRepository sysParamRepository;
    private final SysParamTransform sysParamTransform;

    @Transactional
    public SysParam saveOrUpdate(SysParamVo typeVo) throws Exception{
        SysParam sysParam = sysParamTransform.toEntity(typeVo);
        return super.saveAndFlush(sysParam);
    }

    public SysParam getById(String id) {
        return sysParamRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        sysParamRepository.deleteById(id);
    }

    public List<SysParam> getAll() {
        return sysParamRepository.findAll();
    }

    public Page<SysParamVo> getSysParams(String searchType, String searchCode, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<SysParam> conditionQuery = SysParamSpec.findByCondition(searchType, searchCode);
        Page<SysParam> sysParams = sysParamRepository.findAll(conditionQuery, pageable);
        return sysParamTransform.mapEntityPageIntoDtoPage(sysParams);
    }


    @Override
    public BaseRepository<SysParam, String> getRepository() {
        return sysParamRepository;
    }

    private final SysTypeRepository typeRepository;

    public List<SysParam> getLstByType(String type) {
        SysType sysType = typeRepository.findByCode(type);
        if (sysType == null)
            return null;
        return findAll(SysParamSpec.findByType(sysType.getId()));
    }

    public SysParam getParamByTypeAndCode(String typeCode, String code) {
        List<SysParam> params =  findAll(SysParamSpec.findByTypeAndCode(typeCode, code));
        return params == null ? null : params.get(0);
    }

    public SysParam getApplyingPosition(String subjectEmail) {
        String normalizedEmail = subjectEmail.toLowerCase().replaceAll("\\s+", "");
        Integer id = typeRepository.findIdByCode("APPLY_POSITION").orElse(null);
        if (id != null) {
            return findAll(SysParamSpec.findByType(id)).stream()
                    .filter(sysParam -> normalizedEmail.contains(sysParam.getValue().toLowerCase().replaceAll("\\s+", "")))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
