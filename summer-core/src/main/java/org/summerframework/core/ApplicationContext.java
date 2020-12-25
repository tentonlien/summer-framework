package org.summerframework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.summerframework.core.annotation.Bean;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tenton Lien
 * @date 12/24/2020
 */
public class ApplicationContext {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationContext.class);

    private static Map<String, Object> beans = new HashMap<>();

    public static Object getBean(String name) {
        if (beans.containsKey(name)) {
            return beans.get(name);
        }
        return null;
    }

    public void traverse(File file, String prefix) {
        File[] files = file.listFiles();

        if (files != null) {
            for (File element: files) {
                if (element.isDirectory()) {
                    traverse(element, prefix + element.getName() + ".");
                } else {
                    if (element.getName().contains(".class")) {
                        String targetClassName = prefix + element.getName().substring(0, element.getName().length() - 6);
                        try {
                            Class<?> classType = Class.forName(targetClassName);
                            if (classType.getAnnotation(Bean.class) != null) {
                                beans.put(targetClassName, classType.newInstance());
                            }
                        } catch (ClassNotFoundException e) {
                            logger.error("Class Not Found: {}", targetClassName);
                        } catch (IllegalAccessException | InstantiationException e) {
                            logger.error(e.toString());
                        }
                    }
                }
            }
        }
    }

    public ApplicationContext() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("");
        if (url != null) {
            traverse(new File(url.getPath()), "");
        } else {
            logger.error("Resource URI is null");
            System.exit(-1);
        }
    }
}
