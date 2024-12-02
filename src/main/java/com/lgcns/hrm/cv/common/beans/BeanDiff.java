package com.lgcns.hrm.cv.common.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class BeanDiff implements Serializable {

    @JsonIgnore
    private final transient Set<String> fields = new HashSet<>();

    @JsonIgnore
    private final transient Map<String, Object> oldValues = new HashMap<>();

    @JsonIgnore
    private final transient Map<String, Object> newValues = new HashMap<>();
}
