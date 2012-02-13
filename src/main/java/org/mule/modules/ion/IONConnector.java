/**
 * Mule Development Kit
 * Copyright 2010-2011 (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.modules.ion;

import com.github.jeluard.ion.Connection;
import com.github.jeluard.ion.DomainConnection;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

import java.io.File;

/**
 * iON Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Module(name="ion", schemaVersion="1.0")
public class IONConnector {

    /**
     * iON URL.
     */
    @Configurable
    @Optional
    @Default(value=Connection.DEFAULT_URL)
    private String url;

    /**
     * iON username.
     */
    @Configurable
    private String username;

    /**
     * iON password.
     */
    @Configurable
    private String password;

    /**
     * iON domain.
     */
    @Configurable
    private String domain;

    /**
     * Mule version used.
     */
    @Configurable
    @Optional
    @Default(value="3.2.1")
    private String muleVersion;

    /**
     * Number of workers allocated.
     */
    @Configurable
    @Optional
    @Default(value="1")
    private Integer workers;

    /**
     * Maximum time allowed to deplpoy/undeploy.
     */
    @Configurable
    @Optional
    @Default(value="120000")
    private Long maxWaitTime;

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public void setMuleVersion(final String muleVersion) {
        this.muleVersion = muleVersion;
    }

    public void setWorkers(final Integer workers) {
        this.workers = workers;
    }

    public void setMaxWaitTime(final Long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    /**
     * Deploy specified application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:deploy}
     *
     * @param file mule application to deploy
     */
    @Processor
    public void deploy(final File file) {
        final DomainConnection domainConnection = new Connection(this.url, this.username, this.password).on(this.domain);
        domainConnection.deploy(file, this.muleVersion, this.workers, this.maxWaitTime);
    }

    /**
     * Undeploy currently deployed application.
     *
     * {@sample.xml ../../../doc/ION-connector.xml.sample ion:undeploy}
     */
    @Processor
    public void undeploy() {
        final DomainConnection domainConnection = new Connection(this.url, this.username, this.password).on(this.domain);
        domainConnection.undeploy(this.maxWaitTime);
    }

}