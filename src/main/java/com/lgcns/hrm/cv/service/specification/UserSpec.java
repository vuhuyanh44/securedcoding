package com.lgcns.hrm.cv.service.specification;

import com.lgcns.hrm.cv.common.jpa.Specifications;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.model.vo.UserSearchVo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpec {
    public static Specification<User> findByCondition(UserSearchVo userSearchParam) {
        return Specifications.<User>and()
                .like(ObjectUtil.isNotEmpty(userSearchParam.getName()) && ObjectUtil.isNotNull(userSearchParam.getName()),"name","%" + userSearchParam.getName() + "%")
                .like(ObjectUtil.isNotEmpty(userSearchParam.getCode()),"code","%" + userSearchParam.getCode() + "%")
                .eq(ObjectUtil.isNotEmpty(userSearchParam.getDepartmentId()),"department.id",userSearchParam.getDepartmentId())
                .eq(ObjectUtil.isNotEmpty(userSearchParam.getDevSide()),"devSide",userSearchParam.getDevSide())
                .eq(ObjectUtil.isNotEmpty(userSearchParam.getDuty()),"duty",userSearchParam.getDuty())
                .like(ObjectUtil.isNotEmpty(userSearchParam.getTypeOfJob()),"typeOfJob","%" + userSearchParam.getTypeOfJob() + "%")
                .eq(ObjectUtil.isNotEmpty(userSearchParam.getSex()),"sex",userSearchParam.getSex())
                .like(ObjectUtil.isNotEmpty(userSearchParam.getPhoneNumber()),"phoneNumber", "%" + userSearchParam.getPhoneNumber() + "%")
                .like(ObjectUtil.isNotEmpty(userSearchParam.getEmail()),"email", "%" + userSearchParam.getEmail() + "%")
                .build();
    }
}
