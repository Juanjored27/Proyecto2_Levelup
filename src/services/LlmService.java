package services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import config.ConfigLoader;
import model.Producto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LlmService {
    private final HttpClient client;
    private final Gson gson;

    public LlmService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String enviarPrompt(String prompt) {
        try {
            String apiKey = ConfigLoader.getOpenRouterApiKey();
            String model = ConfigLoader.getOpenRouterModel();
            String url = ConfigLoader.getOpenRouterUrl();
            String referer = ConfigLoader.getOpenRouterReferer();
            String appName = ConfigLoader.getOpenRouterAppName();

            if (apiKey == null || apiKey.isBlank() || "CAMBIAR_API_KEY".equals(apiKey)) {
                return "Error: configura tu API key de OpenRouter en config.properties para usar la Fase 5.";
            }

            JsonObject body = new JsonObject();
            body.addProperty("model", model);

            JsonArray messages = new JsonArray();

            JsonObject systemMsg = new JsonObject();
            systemMsg.addProperty("role", "system");
            systemMsg.addProperty("content", "Eres un asistente para una tienda de videojuegos y merchandising llamada LevelUp Arcade. Responde en español de España, de forma breve y útil.");
            messages.add(systemMsg);

            JsonObject userMsg = new JsonObject();
            userMsg.addProperty("role", "user");
            userMsg.addProperty("content", prompt);
            messages.add(userMsg);

            body.add("messages", messages);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .header("HTTP-Referer", referer)
                    .header("X-Title", appName)
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return "Error OpenRouter. Código HTTP: " + response.statusCode() + "\n" + response.body();
            }

            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            if (jsonResponse == null || !jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").isEmpty()) {
                return "Error: respuesta JSON no válida o sin contenido generado.";
            }

            JsonObject firstChoice = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject();
            if (!firstChoice.has("message")) {
                return "Error: la respuesta no contiene el campo message.";
            }

            JsonObject message = firstChoice.getAsJsonObject("message");
            if (!message.has("content")) {
                return "Error: la respuesta no contiene el texto generado.";
            }

            return message.get("content").getAsString();

        } catch (IOException | InterruptedException e) {
            return "Error de conexión con OpenRouter: " + e.getMessage();
        } catch (Exception e) {
            return "Error procesando la respuesta de IA: " + e.getMessage();
        }
    }

    public String generarDescripcionProducto(Producto producto) {
        if (producto == null) {
            return "Error: producto no encontrado.";
        }

        String prompt = "Genera una descripción comercial breve y atractiva para este producto de una tienda llamada LevelUp Arcade. " +
                "Nombre: " + producto.getNombre() + ". " +
                "Descripción base: " + valorSeguro(producto.getDescripcion()) + ". " +
                "Precio: " + producto.getPrecio() + " euros. " +
                "Stock actual: " + producto.getStock() + ". " +
                "Devuelve solo la descripción final en 2 o 3 frases.";

        return enviarPrompt(prompt);
    }

    public String sugerirCategoriaProducto(Producto producto) {
        if (producto == null) {
            return "Error: producto no encontrado.";
        }

        String prompt = "Sugiere la categoría más adecuada para este producto de una tienda de videojuegos y merchandising. " +
                "Nombre: " + producto.getNombre() + ". " +
                "Descripción: " + valorSeguro(producto.getDescripcion()) + ". " +
                "Responde solo con una categoría breve, por ejemplo: Videojuegos, Merchandising, Consolas, Accesorios.";

        return enviarPrompt(prompt);
    }

    private String valorSeguro(String texto) {
        return texto == null || texto.isBlank() ? "Sin descripción" : texto;
    }
}
