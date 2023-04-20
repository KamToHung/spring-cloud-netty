/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netty.server.embedded;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;

/**
 * HTTP服务初始化完成事件
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 */
public class HttpServerInitializedEvent extends WebServerInitializedEvent {

    private final HttpServerApplicationContext applicationContext;

    public HttpServerInitializedEvent(WebServer webServer,
                                      HttpServerApplicationContext applicationContext) {
        super(webServer);
        this.applicationContext = applicationContext;
    }

    @Override
    public HttpServerApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

}
