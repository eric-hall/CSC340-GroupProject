package com.restready.gui;

import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

public class TestApiUser {

    public record HttpResponse(HttpRequestMethod requestMethod, int code, String message) {
        public void print() {
            System.out.printf("%s request response (code %d): %s%n", requestMethod, code, message);
        }
    }

    public enum HttpRequestMethod {
        GET, POST, PUT, DELETE
    }

    public static void main(String[] args) {

        try {

            String baseUrl = "http://localhost:8080/api";

            // Test GET, POST, PUT, and DELETE
            HttpResponse getResponse = sendGetRequest(baseUrl);
            HttpResponse postResponse = sendPostRequest(baseUrl, "{'key': 'value'}");
            HttpResponse putResponse = sendPutRequest(baseUrl, "{'key': 'updated'}");
            HttpResponse deleteResponse = sendDeleteRequest(baseUrl);

            getResponse.print();
            postResponse.print();
            putResponse.print();
            deleteResponse.print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse sendGetRequest(String url) throws Exception {
        return sendHttpRequest(url, HttpRequestMethod.GET, null);
    }

    public static HttpResponse sendPostRequest(String url, String postData) throws Exception {
        return sendHttpRequest(url, HttpRequestMethod.POST, postData);
    }

    public static HttpResponse sendPutRequest(String url, String putData) throws Exception {
        return sendHttpRequest(url, HttpRequestMethod.PUT, putData);
    }

    public static HttpResponse sendDeleteRequest(String url) throws Exception {
        return sendHttpRequest(url, HttpRequestMethod.DELETE, null);
    }

    private static HttpResponse sendHttpRequest(String baseUrl, HttpRequestMethod requestMethod, String json) throws URISyntaxException, IOException {

        // Open the url connection and set request method
        URL url = new URI(baseUrl + "/" + requestMethod.name().toLowerCase()).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod.name());

        // Output json to the connection, if any
        if (json != null) {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();
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
