package adapter;

/**
 * @Description:
 * @Author: csc
 * @Create: 2023-05-19
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundHandlerEncode2 extends MessageToByteEncoder {
    private static Logger logger = LoggerFactory.getLogger(OutboundHandlerEncode2.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("OutboundHandlerEncode2.write");
        // 执行下一个OutboundHandler
        super.write(ctx, msg, promise);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        logger.info("OutboundHandlerEncode2.encode");
    }


}
