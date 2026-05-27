package util;

import config.ConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.*;

public class LoggerUtil {
    private static final Logger LOGGER = Logger.getLogger("LevelUpLogger");

    static {
        try {
            LogManager.getLogManager().reset();
            Path logPath = Path.of(ConfigLoader.getLogPath());
            if (logPath.getParent() != null) {
                Files.createDirectories(logPath.getParent());
            }
            FileHandler fh = new FileHandler(logPath.toString(), true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("No se pudo inicializar el logger: " + e.getMessage());
        }
    }

    private LoggerUtil() {}
    public static Logger getLogger() { return LOGGER; }
}
