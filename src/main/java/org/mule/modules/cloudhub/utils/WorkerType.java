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

public enum WorkerType {
    Micro("Micro"),
    Small("Small"),
    Medium("Medium"),
    Large("Large"),
    xLarge("xLarge");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }

    private WorkerType(String name) {
        this.name = name;
    }
}
