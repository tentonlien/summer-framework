package org.summerframework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Tenton Lien
 */
public class SummerApplication {
    public static void run(Class _class, String[] args) {
        printBanner();
        ApplicationContext applicationContext = new ApplicationContext();
    }

    private static void printBanner() {
        InputStream is = SummerApplication.class.getClassLoader().getResourceAsStream("banner.txt");
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is)
        );
        String str = null;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
