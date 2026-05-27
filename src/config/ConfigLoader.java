package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        cargarConfiguracion();
    }

    private ConfigLoader() {}

    private static void cargarConfiguracion() {
        String[] rutas = {
                "config.properties",
                "./config.properties",
                "../config.properties"
        };

        for (String ruta : rutas) {
            File file = new File(ruta);
            if (file.exists() && file.isFile()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    properties.load(fis);
                    System.out.println("Configuración cargada desde: " + file.getPath());
                    aplicarValoresPorDefecto();
                    return;
                } catch (IOException e) {
                    System.err.println("Error al leer " + file.getPath() + ": " + e.getMessage());
                }
            }
        }

        try (InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                System.out.println("Configuración cargada desde classpath.");
            } else {
                System.err.println("No se encontró config.properties. Se usarán valores por defecto.");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar config.properties desde classpath: " + e.getMessage());
        }

        aplicarValoresPorDefecto();
    }

    private static void aplicarValoresPorDefecto() {
        properties.putIfAbsent("db.url", "jdbc:mysql://192.168.1.183:3306/levelup_arcade?useSSL=false&serverTimezone=Europe/Madrid&allowPublicKeyRetrieval=true");
        properties.putIfAbsent("db.user", "root");
        properties.putIfAbsent("db.password", "campusfp");
        properties.putIfAbsent("log.path", "logs/app.log");
        properties.putIfAbsent("app.name", "LevelUp Arcade");
        properties.putIfAbsent("openrouter.api.key", "CAMBIAR_API_KEY");
        properties.putIfAbsent("openrouter.model", "openrouter/free");
        properties.putIfAbsent("openrouter.url", "https://openrouter.ai/api/v1/chat/completions");
        properties.putIfAbsent("openrouter.referer", "http://localhost");
        properties.putIfAbsent("openrouter.appname", "LevelUpArcade");
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        return value != null ? value.trim() : "";
    }

    public static String getDbUrl() { return getProperty("db.url"); }
    public static String getDbUser() { return getProperty("db.user"); }
    public static String getDbPassword() { return getProperty("db.password"); }
    public static String getLogPath() { return getProperty("log.path"); }
    public static String getOpenRouterApiKey() { return getProperty("openrouter.api.key"); }
    public static String getOpenRouterModel() { return getProperty("openrouter.model"); }
    public static String getOpenRouterUrl() { return getProperty("openrouter.url"); }
    public static String getOpenRouterReferer() { return getProperty("openrouter.referer"); }
    public static String getOpenRouterAppName() { return getProperty("openrouter.appname"); }
    public static String getAppName() { return getProperty("app.name"); }
}
