package com.lgcns.hrm.cv.common.beans;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class BaseBeanMapKey {
    private final Class type;
    private final int require;
}

