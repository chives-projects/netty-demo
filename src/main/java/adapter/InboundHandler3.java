package adapter;

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

public class InboundHandler3 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(InboundHandler3.class);

    @Override
    // 接收发送的信息，并打印出来
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("InboundHandler3.channelRead: ctx :" + ctx);
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        String resultStr = new String(result1);
        logger.info("receive message:" + resultStr);
        result.release();

        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        logger.info("InboundHandler3.channelReadComplete");
        ctx.flush();
    }

}