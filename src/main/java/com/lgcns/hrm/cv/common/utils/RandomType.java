package com.lgcns.hrm.cv.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RandomType {
    /**
     * INT STRING ALL
     */
    INT(RandomType.INT_STR),
    STRING(RandomType.STR_STR),
    ALL(RandomType.ALL_STR);

    private final String factor;

    private static final String INT_STR = "0123456789";
    private static final String STR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL_STR = INT_STR + STR_STR;
}