package clientpush;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;
import java.util.Scanner;

public class ClientPush {
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
                    ch.pipeline().addLast("decoder", new StringDecoder());
                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new HeartBeatClientHandler());
                }
            });

            // Start the client.
            ChannelFuture channelFuture = b.connect(host, port).sync();

            //获取通道，模拟发送心跳包
            Channel channel = channelFuture.channel();
            String text = "Heartbeat Packet:";
//            Random random = new Random();
//            while (channel.isActive()) {
//                int num = random.nextInt(8); //随机睡眠
//                Thread.sleep(num * 1000);
//                channel.writeAndFlush(text);
//            }

            // 客户端需要输入信息，创建扫描器 scanner
            Scanner scanner = new Scanner(System.in);
            while (channel.isActive() && scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 通过 channel 发送到服务器端
//                if (channel.isActive())
                channel.writeAndFlush(text + msg + "\r\n");
            }
            scanner.close();
            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        ClientPush client = new ClientPush();
        client.connect("127.0.0.1", 8000);
    }
}
