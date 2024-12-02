package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.entity.SysType;
import com.lgcns.hrm.cv.model.vo.SysTypeVo;
import com.lgcns.hrm.cv.repository.SysTypeRepository;
import com.lgcns.hrm.cv.repository.base.BaseRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.SysTypeSpec;
import com.lgcns.hrm.cv.transform.SysTypeTransform;
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
public class SysTypeService extends AbstractBaseService<SysType, Integer> {

    private final SysTypeRepository sysTypeRepository;
    private final SysTypeTransform sysTypeTransform;

    @Transactional
    public void saveOrUpdate(SysTypeVo typeVo) throws Exception{
        SysType sysType = sysTypeTransform.toEntity(typeVo);
        super.saveAndFlush(sysType);
    }

    public SysType getById(Integer id) {
        return sysTypeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        sysTypeRepository.deleteById(id);
    }

    public List<SysType> getAll() {
        return sysTypeRepository.findAll();
    }

    public Page<SysTypeVo> getSysTypes(String searchName, String searchCode, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<SysType> conditionQuery = SysTypeSpec.findByCondition(searchName, searchCode);
        Page<SysType> sysTypes = sysTypeRepository.findAll(conditionQuery, pageable);
        return sysTypeTransform.mapEntityPageIntoDtoPage(sysTypes);
    }


    @Override
    public BaseRepository<SysType, Integer> getRepository() {
        return sysTypeRepository;
    }
}
