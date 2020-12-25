package org.summerframework.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tenton Lien
 */
public class NettyServer {

    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final int serverPort;
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    public NettyServer(int port) {
        this.serverPort = port;
    }

    public void runServer() {
        // Create reactor thread groups
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            // Set reactor thread groups
            serverBootstrap.group(bossLoopGroup, workerLoopGroup);
            // Set NIO channel
            serverBootstrap.channel(NioServerSocketChannel.class);
            // Set listening port
            serverBootstrap.localAddress(serverPort);
            // Set channel params
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // Handle pipeline of child channel
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                // When a connection arrives, create a channel
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyHandler());
                }
            });
            // Start binding server
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            logger.info("Server starts successfully, listening address: " + channelFuture.channel().localAddress());

            // Wait util the async task of closing channel finishing
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }
}