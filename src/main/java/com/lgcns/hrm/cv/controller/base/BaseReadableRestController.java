package com.lgcns.hrm.cv.controller.base;

import com.lgcns.hrm.cv.common.definition.domain.AbstractEntity;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.common.domain.vo.Pager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseReadableRestController<E extends AbstractEntity, M extends AbstractVo, ID extends Serializable> implements ReadableController<E, M, ID> {


    @Operation(summary = "Paging query data", description = "Get paging data through pageNumber and pageSize",
            responses = {
                    @ApiResponse(description = "unit list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "204", description = "Query successful, no data found"),
                    @ApiResponse(responseCode = "500", description = "Query failed")
            })
    @Parameters({
            @Parameter(name = "pager", required = true, in = ParameterIn.QUERY, description = "Paging Bo object", schema = @Schema(implementation = Pager.class))
    })
    @GetMapping
    public Result<Map<String, Object>> findByPage(@Validated Pager pager) {
        if (ArrayUtils.isNotEmpty(pager.getProperties())) {
            Sort.Direction direction = Sort.Direction.valueOf(pager.getDirection());
            return ReadableController.super.findByPage(pager.getPageNumber(), pager.getPageSize(), direction, pager.getProperties());
        } else {
            return ReadableController.super.findByPage(pager.getPageNumber(), pager.getPageSize());
        }
    }
}
