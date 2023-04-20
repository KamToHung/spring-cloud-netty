package com.netty.server;

import com.netty.handler.HttpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 *
 * @author <a href = "kamtohung@gmail.com">hongjintao</a>
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final HttpServerHandler INSTANCE = new HttpServerHandler();

    //SSL开关
    private static final boolean SSL = false;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //配置SSL
        final SslContext sslCtx;
        if (SSL) {
            //netty为我们提供的ssl加密，缺省
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(),
                    ssc.privateKey()).build();
            //验证SSL
            pipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        //对http请求进行解码
        pipeline.addLast("decode", new HttpRequestDecoder());

        //对http请求进行编码
        pipeline.addLast("encode", new HttpResponseEncoder());

        //对http进行聚合，设置最大聚合字节长度为10M
        pipeline.addLast(new HttpObjectAggregator(10 * 1024 * 1024));

        //开启http内容压缩
        pipeline.addLast(new HttpContentCompressor());

        //添加自定义处理器
        pipeline.addLast(HttpServerHandler.class.getName(),INSTANCE);
    }
}
