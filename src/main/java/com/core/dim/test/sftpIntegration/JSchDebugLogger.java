package com.core.dim.test.sftpIntegration;

import com.jcraft.jsch.Logger;

public class JSchDebugLogger implements Logger {
    static java.util.Hashtable<String, Integer> levels = new java.util.Hashtable<>();

    static {
        levels.put("DEBUG", Logger.DEBUG);
        levels.put("INFO", Logger.INFO);
        levels.put("WARN", Logger.WARN);
        levels.put("ERROR", Logger.ERROR);
        levels.put("FATAL", Logger.FATAL);
    }

    @Override
    public boolean isEnabled(int level) {
        return true; // Enable all levels
    }

    @Override
    public void log(int level, String message) {
        System.out.println("[JSch][" + levelToString(level) + "] " + message);
    }

    private String levelToString(int level) {
        for (String key : levels.keySet()) {
            if (levels.get(key) == level) {
                return key;
            }
        }
        return "UNKNOWN";
    }
}
