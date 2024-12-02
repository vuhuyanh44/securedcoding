package com.lgcns.hrm.cv.controller;

import com.lgcns.hrm.cv.common.domain.Result;
import com.lgcns.hrm.cv.model.vo.AuthenticationVo;
import com.lgcns.hrm.cv.model.vo.UserGroupsPermissionVo;
import com.lgcns.hrm.cv.model.vo.LoginVo;
import com.lgcns.hrm.cv.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author pigx
 */
@Tag(name = "Authentication", description = "Authentication Management API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Login to the system using username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully.",
                    content = @Content(schema = @Schema(implementation = AuthenticationVo.class)))
    })
    @PostMapping("/login")
    public Result<AuthenticationVo> authenticate(
            @Valid @RequestBody LoginVo request
    ) {

        return Result.success("Login Success", service.authenticate(request));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/groups-permissions")
    public Result<UserGroupsPermissionVo> getGroupAndPermissionFromUser() {
        return Result.success("Successfully", service.getGroupAndPermissionForUser());
    }
}