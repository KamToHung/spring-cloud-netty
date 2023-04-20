package com.netty.server.embedded;

import com.netty.server.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;

/**
 * 服务启动生命周期
 *
 * @author <a href = "kamtohung@gmail.com">KamTo Hung</a>
 */
public class HttpServerLifecycle implements SmartLifecycle {

    private final ApplicationContext applicationContext;

    private final HttpServer webServer;

    private volatile boolean running;

    public HttpServerLifecycle(ApplicationContext applicationContext, HttpServer webServer) {
        this.applicationContext = applicationContext;
        this.webServer = webServer;
    }

    @Override
    public void start() {
        new Thread(this.webServer::start).start();
        this.running = true;
        this.applicationContext.publishEvent(new HttpServerInitializedEvent(this.webServer,
                new HttpServerApplicationContext(this.webServer)));
    }

    @Override
    public void stop() {
        this.webServer.stop();
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE - 1;
    }
}
