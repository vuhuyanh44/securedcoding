package com.lgcns.hrm.cv.common.utils;
import com.lgcns.hrm.cv.common.constants.CharPool;
import com.lgcns.hrm.cv.common.constants.SymbolConstants;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
public class RuntimeUtil {
    private static volatile int pId = -1;
    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();


    public static int getPId() {
        if (pId > 0) {
            return pId;
        }
        // something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf(CharPool.AT);
        if (index > 0) {
            pId = NumberUtil.toInt(jvmName.substring(0, index), -1);
            return pId;
        }
        return pId;
    }


    public static Instant getStartTime() {
        return Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());
    }

    public static Duration getUpTime() {
        return Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
    }

    public static String getJvmArguments() {
        List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        return StringUtil.join(vmArguments, SymbolConstants.SPACE);
    }
    public static int getCpuNum() {
        return CPU_NUM;
    }

}
