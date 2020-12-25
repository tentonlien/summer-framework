package org.summerframework.core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * @author Tenton Lien
 * @date 12/24/2020
 */
public class SummerApplication {

    private final static Logger logger = LoggerFactory.getLogger(SummerApplication.class);

    public static void run(Class aClass, String[] args) {
        printBanner();

        PropertiesManager.load();

        // Set global log level as INFO instead of DEBUG
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger targetLogger = loggerContext.getLogger("root");
        targetLogger.setLevel(Level.toLevel("INFO"));

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} %yellow(%5level) [%t] %cyan(%logger{36}) : %msg%n");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender =
                (ConsoleAppender<ILoggingEvent>)targetLogger.getAppender("CONSOLE");
        appender.setContext(loggerContext);
        appender.setEncoder(encoder);
        appender.start();
        targetLogger.addAppender(appender);

        // Read PID
        String[] processInfo = ManagementFactory.getRuntimeMXBean().getName().split("@");
        String pid = processInfo[0];
        String hostName = processInfo[1];
        String caller = new Throwable().getStackTrace()[1].getClassName();
        logger.info("Starting {} on {} with PID {}", caller, hostName, pid);

        ApplicationContext applicationContext = new ApplicationContext();
    }

    private static void printBanner() {
        InputStream is = SummerApplication.class.getClassLoader().getResourceAsStream("banner.txt");
        assert is != null;
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is)
        );
        String str;
        try {
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }
}
