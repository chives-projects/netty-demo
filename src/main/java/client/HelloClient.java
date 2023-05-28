package client;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import adapter.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloClient {
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new OutboundHandler1());
//                    ch.pipeline().addLast(new OutboundHandler2());
//                    ch.pipeline().addLast(new OutboundHandler3());
//
//                    ch.pipeline().addLast(new InboundHandler1());
//                    ch.pipeline().addLast(new InboundHandler2());
////                    ch.pipeline().addLast(new InboundHandler3());


                    ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ClientInHandler());
                }
            });

            // Start the client.
            ChannelFuture channelFuture = b.connect(host, port).sync();
//            f.channel().closeFuture().sync();
            //获取通道，模拟发送心跳包
            Channel channel = channelFuture.channel();
            String text = "Heartbeat Packet";
            Random random = new Random();
            while (channel.isActive()) {
                int num = random.nextInt(8); //随机睡眠
                Thread.sleep(num * 1000);
                channel.writeAndFlush(text);
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HelloClient client = new HelloClient();
        client.connect("127.0.0.1", 8000);
    }
}
