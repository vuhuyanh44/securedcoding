package com.lgcns.hrm.cv.controller.base;

import com.lgcns.hrm.cv.common.definition.domain.AbstractEntity;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.service.base.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public abstract class BaseWriteableRestController<E extends AbstractEntity, M extends AbstractVo, ID extends Serializable>
        extends BaseReadableRestController<E, M, ID>
        implements WriteableController<E, M, ID> {

    @Override
    public BaseService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Operation(summary = "Save or update data", description = "Receive JSON data, convert it into entities, and save or update",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "Saved data", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "json data that can be converted into entities")
    })
    @PostMapping
    @Override
    public Result<M> saveOrUpdate(@RequestBody M domainVo) throws Exception{
        return WriteableController.super.saveOrUpdate(domainVo);
    }

    @Operation(summary = "Delete data", description = "Delete data based on entity ID, and associated associated data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "Operation Message", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "Entity ID, entity attribute corresponding to the @Id annotation")
    })
    @DeleteMapping("/{id}")
    @Override
    public Result<String> delete(@PathVariable ID id) {
        return WriteableController.super.delete(id);
    }
}