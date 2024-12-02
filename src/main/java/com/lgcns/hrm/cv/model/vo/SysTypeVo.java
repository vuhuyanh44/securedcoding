package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.entity.SysParam;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysTypeVo extends AbstractVo {

    private String id;
    private String code;
    private String name;
}
