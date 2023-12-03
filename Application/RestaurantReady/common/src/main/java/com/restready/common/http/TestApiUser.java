package com.restready.common.http;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.util.stream.Collectors;

public class TestApiUser {

    public static final String BASE_URL = "http://localhost:8080/api";

    public static void main(String[] args) throws Exception {
        JavaNetHttpPackage.runTest(BASE_URL);
        JavaNetPackage.runTest(BASE_URL);
    }

    private static class JavaNetHttpPackage {

        public static void runTest(String baseUrl) throws Exception {

            URI path = new URI(baseUrl);

            try (HttpClient client = HttpClient.newHttpClient()) {

                HttpResponse<String> getResponse = sendGetRequest(client, path);
                HttpResponse<String> postResponse = sendPostRequest(client, path, "{'key': 'value'}");
                HttpResponse<String> putResponse = sendPutRequest(client, path, "{'key': 'updated'}");
                HttpResponse<String> deleteResponse = sendDeleteRequest(client, path);

                printResponse(getResponse);
                printResponse(postResponse);
                printResponse(putResponse);
                printResponse(deleteResponse);
            }
        }

        private static void printResponse(HttpResponse<String> response) {
            System.out.printf("%s request response (code %d): %s%n", response.request().method(), response.statusCode(), response.body());
        }

        private static HttpResponse<String> sendGetRequest(HttpClient httpClient, URI path) throws Exception {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(path)
                    .GET()
                    .build();
            return sendRequest(httpClient, request);
        }

        private static HttpResponse<String> sendPostRequest(HttpClient httpClient, URI path, String postData) throws Exception {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(path)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postData))
                    .build();
            return sendRequest(httpClient, request);
        }

        private static HttpResponse<String> sendPutRequest(HttpClient httpClient, URI path, String putData) throws Exception {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(path)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(putData))
                    .build();
            return sendRequest(httpClient, request);
        }

        private static HttpResponse<String> sendDeleteRequest(HttpClient httpClient, URI path) throws Exception {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(path)
                    .DELETE()
                    .build();
            return sendRequest(httpClient, request);
        }

        private static HttpResponse<String> sendRequest(HttpClient httpClient, HttpRequest request) throws Exception {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    private static class JavaNetPackage {

        private record HttpResponse(HttpRequestMethod requestMethod, int code, String message) {
            public void print() {
                System.out.printf("%s request response (code %d): %s%n", requestMethod, code, message);
            }
        }

        private enum HttpRequestMethod {
            GET, POST, PUT, DELETE
        }

        private static void runTest(String baseUrl) throws Exception {

            URI path = new URI(baseUrl);

            HttpResponse getResponse = sendGetRequest(path);
            HttpResponse postResponse = sendPostRequest(path, "{'key': 'value'}");
            HttpResponse putResponse = sendPutRequest(path, "{'key': 'updated'}");
            HttpResponse deleteResponse = sendDeleteRequest(path);

            getResponse.print();
            postResponse.print();
            putResponse.print();
            deleteResponse.print();
        }

        private static HttpResponse sendGetRequest(URI path) throws Exception {
            return sendHttpRequest(path, HttpRequestMethod.GET, null);
        }

        private static HttpResponse sendPostRequest(URI path, String postData) throws Exception {
            return sendHttpRequest(path, HttpRequestMethod.POST, postData);
        }

        private static HttpResponse sendPutRequest(URI path, String putData) throws Exception {
            return sendHttpRequest(path, HttpRequestMethod.PUT, putData);
        }

        private static HttpResponse sendDeleteRequest(URI path) throws Exception {
            return sendHttpRequest(path, HttpRequestMethod.DELETE, null);
        }

        private static HttpResponse sendHttpRequest(URI path, HttpRequestMethod requestMethod, String json) throws IOException {

            // Open the url connection and set request method
            URL url = path.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod.name());

            // Output json to the connection, if any
            if (json != null) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(json.getBytes());
                    os.flush();
                }
            }

            // Collect the response information
            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseMessage = in.lines().collect(Collectors.joining());
            in.close();

            // Close the connection and return the response
            connection.disconnect();
            return new HttpResponse(requestMethod, responseCode, responseMessage);
        }
    }
}
