package org.summerframework.demo.controller;

import org.summerframework.core.annotation.Bean;

/**
 * @author Tenton Lien
 */
@Bean
public class EchoController {
    public String echo(String content) {
        return "Echo: " + content;
    }
}
