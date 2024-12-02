package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.SysType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SysTypeSpec {
    public static Specification<SysType> findByCondition(String searchName, String searchCode) {
        return Specifications.<SysType>and()
                .like(ObjectUtil.isNotEmpty(searchCode),"code","%" + searchCode + "%")
                .like(ObjectUtil.isNotEmpty(searchName),"name","%" + searchName + "%")
                .asc("createTime")
                .build();
    }
}
