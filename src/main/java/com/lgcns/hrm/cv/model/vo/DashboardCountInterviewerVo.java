package com.lgcns.hrm.cv.model.vo;

import com.lgcns.hrm.cv.common.domain.vo.BaseVo;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 * @author pigx
 * @created 16/01/2024 - 10:03 AM
 * @project hr-api
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardCountInterviewerVo extends BaseVo {
    String name;
    String code;
    int total;
}
