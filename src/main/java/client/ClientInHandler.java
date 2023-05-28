package client;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientInHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientInHandler.class);

    @Override
    // 读取服务端的信息
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("HelloClientIntHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        result.release();
        ctx.close();
        logger.info("Server said:" + new String(result1));
    }

    @Override
    // 当连接建立的时候向服务端发送消息 ，channelActive 事件当连接建立的时候会触发
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("HelloClientIntHandler.channelActive");
        String msg = "Are you ok?";
        logger.info("req to server:" + msg);
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
