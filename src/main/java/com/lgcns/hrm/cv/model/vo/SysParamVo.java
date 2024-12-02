package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.entity.SysType;
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
public class SysParamVo extends AbstractVo {

    private String id;
    private String code;
    private String value;
    private int order;
    private SysType sysType;
}
