package org.com.utils;

import java.security.SecureRandom;

public class IdUtils {
    public static long generateTenDigitalId() {
        SecureRandom secureRandom = new SecureRandom();
        final long MIN_ACCOUNT = 1_000_000_000L; // 10位最小值
        final long MAX_ACCOUNT = 9_999_999_999L; // 10位最大值
            // 生成10位随机long值
                return MIN_ACCOUNT + (long)(secureRandom.nextDouble() * (MAX_ACCOUNT - MIN_ACCOUNT + 1));
    }
}
