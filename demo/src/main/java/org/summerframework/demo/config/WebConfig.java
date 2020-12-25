package org.summerframework.demo.config;

import org.summerframework.core.annotation.Bean;
import org.summerframework.web.NettyServer;

/**
 * @author Tenton Lien
 * @date 12/25/2020
 */
@Bean
public class WebConfig {

    public WebConfig() {
        NettyServer nettyServer = new NettyServer();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                nettyServer.runServer();
            }
        }, "web");
        thread.start();
    }
}
