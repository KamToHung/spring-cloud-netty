package com.netty.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;

/**
 * Server Context
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 */
@Data
public class ServerContext {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Class<? extends ServerSocketChannel> serverSocketChannelClass;

    public ServerContext(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
    }

    public ServerContext(int bossThreadCount, int workThreadCount){
        if (Epoll.isAvailable()) {
            bossGroup = new EpollEventLoopGroup(bossThreadCount);
            workerGroup = new EpollEventLoopGroup(workThreadCount);
            serverSocketChannelClass = EpollServerSocketChannel.class;
        } else {
            bossGroup = new NioEventLoopGroup(bossThreadCount);
            workerGroup = new NioEventLoopGroup(workThreadCount);
            serverSocketChannelClass = NioServerSocketChannel.class;
        }
    }
}
