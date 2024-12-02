package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchVo extends AbstractVo {
    private String id;
    private String code;
    private String name;
    private String departmentId;
    private String devSide;
    private String duty;
    private String sex;
    private String typeOfJob;
    private String phoneNumber;
    private String email;
}
