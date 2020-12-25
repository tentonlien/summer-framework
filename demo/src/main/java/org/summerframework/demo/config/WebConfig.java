package org.summerframework.demo.config;

import org.summerframework.core.annotation.Bean;
import org.summerframework.web.NettyServer;

/**
 * @author Tenton Lien
 */
@Bean
public class WebConfig {

    public WebConfig() {
        NettyServer nettyServer = new NettyServer(7086);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                nettyServer.runServer();
            }
        }, "web");
        thread.start();
    }
}
