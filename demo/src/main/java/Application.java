import controller.EchoController;

import java.io.IOException;

/**
 * @author Tenton Lien
 */
public class Application {
    public static void main(String[] args) throws IOException {
        new SummerApplication().start();
        System.out.println(((EchoController)(ApplicationContext.getBean("controller.EchoController"))).echo("Hey"));
    }
}
