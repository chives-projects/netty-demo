package adapter;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundHandler3 extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(OutboundHandler3.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("OutboundHandler3.write");
        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);
    }
}
