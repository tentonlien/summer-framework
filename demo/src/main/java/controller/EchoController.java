package controller;

import annotation.Bean;

/**
 * @author Tenton Lien
 */
@Bean
public class EchoController {

    public EchoController() {
        System.out.println("Initialize: EchoController");
    }

    public String echo(String content) {
        return "Echo: " + content;
    }
}
