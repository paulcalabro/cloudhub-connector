package org.mule.modules.cloudhub.utils;

public enum LogPriority {
    ALL(""),
    ERROR("stop"),
    FATAL("start"),
    INFO("info"),
    SYSTEM("system"),
    CONSOLE("console"),
    WARN("warn"),
    DEBUG("debug");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    private LogPriority(String name) {
        this.name = name;
    }
}
