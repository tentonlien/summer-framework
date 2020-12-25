package org.summerframework.web;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tenton Lien
 * @date 12/25/2020
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            logger.info("Receive message: ");
            StringBuilder requestContent = new StringBuilder();
            while (in.isReadable()) {
                requestContent.append((char) in.readByte());
            }
            HttpRequest httpRequest = new HttpRequest(requestContent.toString());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}