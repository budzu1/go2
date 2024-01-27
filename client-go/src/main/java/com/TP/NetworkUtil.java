package com.TP;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class NetworkUtil {

    private static final String SERVER_URL = "http://localhost:8080"; // Replace with your server URL
    private static HttpClient httpClient = HttpClient.newHttpClient();

    public static CompletableFuture<String> sendPostRequest(String endpoint, String param, String value) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + endpoint))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(param + "=" + value))
                .build();

        return httpClient.sendAsync(request, BodyHandlers.ofString())
                .thenApply(new Function<HttpResponse<String>, String>() {
                    @Override
                    public String apply(HttpResponse<String> response) {
                        return response.body();
                    }
                });
    }

    public static CompletableFuture<String> sendDoublePostRequest(String endpoint, String param, String value,
            String param2, String value2) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + endpoint))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(param + "=" + value + "&" + param2 + "=" + value2))
                .build();

        return httpClient.sendAsync(request, BodyHandlers.ofString())
                .thenApply(new Function<HttpResponse<String>, String>() {
                    @Override
                    public String apply(HttpResponse<String> response) {
                        return response.body();
                    }
                });
    }
}
