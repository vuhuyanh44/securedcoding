package com.lgcns.hrm.cv.common.utils;

import java.security.SecureRandom;
import java.util.Random;

public interface Holder {

    /**
     * RANDOM
     */
    Random RANDOM = new Random();

    /**
     * SECURE_RANDOM
     */
    SecureRandom SECURE_RANDOM = new SecureRandom();

}
