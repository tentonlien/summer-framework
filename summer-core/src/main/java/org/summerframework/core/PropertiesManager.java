package org.summerframework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Tenton Lien
 * @date 12/25/2020
 */
public class PropertiesManager {

    private final static Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    private static Properties properties;

    public static void load() {
        properties = new Properties();
        InputStream inputStream = SummerApplication.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
