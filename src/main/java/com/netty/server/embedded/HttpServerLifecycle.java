package com.netty.server.embedded;

import org.springframework.context.SmartLifecycle;

/**
 * 服务启动生命周期
 * @author <a href = "kamtohung@gmail.com">KamTo Hung</a>
 */
public class HttpServerLifecycle implements SmartLifecycle {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
