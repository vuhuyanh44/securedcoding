package com.lgcns.hrm.cv.common.domain.vo;

import com.lgcns.hrm.cv.annotation.EnumeratedValue;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Sort parameter Vo object")
public class Sorter extends AbstractVo {

    @EnumeratedValue(names = {"ASC", "DESC"}, message = "The sorting value can only be uppercase ASC or DESC")
    @Schema(name = "Sort Direction", title = "The value of the sort direction can only be uppercase ASC or DESC, default value: DESC", defaultValue = "DESC")
    private String direction = "DESC";

    @Schema(name = "Attribute value", title = "Specify the field name for sorting")
    private String[] properties;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }
}
