package com.util;

public class Constants {
    private static final String key = "1234";
    private static final long timeLive = 86_400_000; // 1 dia
    private static final String header = "Authorization";
    private static final String prefixToken = "Bearer ";

    public static String getKey() {
        return key;
    }

    public static long getTimelive() {
        return timeLive;
    }

    public static String getHeader() {
        return header;
    }

    public static String getPrefixtoken() {
        return prefixToken;
    }

}
