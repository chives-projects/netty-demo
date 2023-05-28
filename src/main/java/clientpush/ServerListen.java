package clientpush;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */
public class ServerListen {
    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());

                            /* 加入一个netty 提供 IdleStateHandler
                             * * 说明
                             * 1. IdleStateHandler 是 netty 提供的处理空闲状态的处理器
                             * 2. readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包，检测是否还是连接状态
                             * 3. writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包，检测是否还是连接状态
                             * 4. allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包，检测是否还是连接状态
                             * 5. 当 IdleStateEvent 触发后，就会传递给管道的下一个handler进行处理，通过调用（触发）下一个 handler 的userEventTrigged 方法，
                             * 在该方法中去处理 IdleStateEvent 事件
                             */
                            ch.pipeline().addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler(自定义)
                            ch.pipeline().addLast(new HeartBeatServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerListen server = new ServerListen();
        server.start(8000);
    }

}
