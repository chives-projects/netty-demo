package adapter;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundHandler1 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler1.class);

    @Override
    // 向外 发送消息
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("OutboundHandler1.write");
        String response = "I am ok!";
        logger.info("send message:" + response);
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());

//        ctx.channel().write(encoded);
        ctx.write(encoded);

        ctx.flush();

    }


}
