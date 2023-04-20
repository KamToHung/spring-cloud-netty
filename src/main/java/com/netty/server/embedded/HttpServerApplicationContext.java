package com.netty.server.embedded;

import com.netty.server.HttpServer;
import org.springframework.boot.web.context.ConfigurableWebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * HTTP服务上下文，这里只是为了调用Web初始化后事件才定义
 *
 * @author <a href = "hongjintao@52tt.com">hongjintao</a>
 */
public class HttpServerApplicationContext extends GenericWebApplicationContext implements ConfigurableWebServerApplicationContext {

    private final HttpServer webServer;

    private String serverNamespace;

    public HttpServerApplicationContext(HttpServer webServer) {
        this.webServer = webServer;
    }

    @Override
    public void setServerNamespace(String serverNamespace) {
        this.serverNamespace = serverNamespace;
    }

    @Override
    public WebServer getWebServer() {
        return this.webServer;
    }

    @Override
    public String getServerNamespace() {
        return serverNamespace;
    }

}
