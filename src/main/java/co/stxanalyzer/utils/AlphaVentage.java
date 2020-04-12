package co.stxanalyzer.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AlphaVentage {
    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private String function;
    private String symbol;
    private String interval;
    private static final String API_KEY = "B71KGBZUF4G538AQ";

    public AlphaVentage(String function, String symbol, String interval) {
        this.function = function;
        this.symbol = symbol;
        this.interval = interval;
    }

    public String getUrlForRequest(){
        return BASE_URL + "function=" + function + "&symbol=" + symbol + "&interval=" + interval + "&apikey=" + API_KEY;
    }

    public String getResponseFromAlpha() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.getUrlForRequest()))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
