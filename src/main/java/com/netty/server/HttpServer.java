package com.netty.server;

import com.netty.config.SettingProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.WebServer;
import org.springframework.stereotype.Component;

/**
 * @author <a href = "kamtohung@gmail.com">KamTo Hung</a>
 */
@Slf4j
@Component
public class HttpServer implements WebServer {

    private final int port;

    private final ServerContext serverParam;

    public HttpServer(SettingProperties settingProperties){
        int bossThreadCount = settingProperties.getHttpServer().getBossGroup();
        int workThreadCount = settingProperties.getHttpServer().getWorkerGroup();
        port = settingProperties.getHttpServer().getPort();
        serverParam = new ServerContext(bossThreadCount, workThreadCount);
    }

    @Override
    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(serverParam.getBossGroup(), serverParam.getWorkerGroup())
                    .channel(serverParam.getServerSocketChannelClass())
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new HttpChannelInitializer());
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            ChannelFuture future = bootstrap.bind(port);
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("http server startup success ...");
                } else {
                    log.error("http server startup failed!!!");
                    System.exit(-1);
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("http server start error.", e);
        } finally {
            serverParam.getBossGroup().shutdownGracefully();
            serverParam.getWorkerGroup().shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        serverParam.getBossGroup().shutdownGracefully();
        serverParam.getWorkerGroup().shutdownGracefully();
    }

    @Override
    public int getPort() {
        return this.port;
    }

}
