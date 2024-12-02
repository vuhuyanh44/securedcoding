package com.lgcns.hrm.cv.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lgcns.hrm.cv.common.definition.domain.AbstractVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author pigx
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationVo extends AbstractVo {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
