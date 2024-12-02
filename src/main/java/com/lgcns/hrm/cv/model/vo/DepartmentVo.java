package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import jakarta.persistence.Column;
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
public class DepartmentVo extends AbstractVo {

    private String id;
    private String code;
    private String name;
    private String parentId;
    private Boolean status;
}
