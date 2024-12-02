package com.lgcns.hrm.cv.common.utils;
import com.lgcns.hrm.cv.common.constants.SymbolConstants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
public interface Charsets {

    Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
    String ISO_8859_1_NAME = ISO_8859_1.name();


    Charset GBK = Charset.forName(SymbolConstants.GBK);
    String GBK_NAME = GBK.name();


    Charset UTF_8 = StandardCharsets.UTF_8;
    String UTF_8_NAME = UTF_8.name();
}
