/**
 * Mule CloudHub Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
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
