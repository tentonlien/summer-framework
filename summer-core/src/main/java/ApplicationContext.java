import annotation.Bean;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tenton Lien
 */
public class ApplicationContext {
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
                            System.out.println("Class Not Found: " + targetClassName);
                        } catch (IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
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
            System.out.println("Application Context:");
            beans.forEach((k, v) -> {
                System.out.println(k + " ");
            });
        }
    }
}
