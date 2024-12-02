package com.lgcns.hrm.cv.common.domain.vo;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(title = "Paging parameter Vo object")
public class Pager extends Sorter {

    @NotNull(message = "Page number cannot be empty")
    @Min(value = 0, message = "Page number cannot be negative")
    @Schema(name = "page number", type = "integer", minimum = "0", defaultValue = "0")
    private Integer pageNumber = 0;

    @NotNull(message = "The number of items per page cannot be empty")
    @Min(value = 1, message = "The number of items per page is at least 1")
    @Max(value = 1000, message = "The number of items per page cannot exceed 1000")
    @Schema(name = "Number of data items per page", type = "integer", minimum = "0", maximum = "1000", defaultValue = "10")
    private Integer pageSize = 10;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pageNumber", pageNumber)
                .add("pageSize", pageSize)
                .toString();
    }
}