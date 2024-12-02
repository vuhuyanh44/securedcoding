package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends AbstractVo {

    private Integer id;
    private String userCode;
    private String name;
    private Department department;
    private String title;
    private Date dOB;
    private String sex;
    private String phoneNumber;
    private String email;
    private Collection<Integer> groupIds;
    private boolean isEnabled = true;
}
