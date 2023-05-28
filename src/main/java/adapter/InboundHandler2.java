package adapter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */
public class InboundHandler2 extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(InboundHandler2.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("InboundHandler2.channelRead: ctx :" + ctx);
        // 通知执行下一个InboundHandler
        ctx.fireChannelRead(msg);
//        ctx.write(msg);
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        logger.info("InboundHandler2.channelReadComplete");
        ctx.flush();
    }

}

