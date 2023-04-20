package com.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href = "kamtohung@gmail.com">KamTo Hung</a>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "setting")
public class SettingProperties {

    private HttpServer httpServer = new HttpServer();

    @Data
    public static class HttpServer {

        private int port;

        private int bossGroup;

        private int workerGroup;

    }

}
