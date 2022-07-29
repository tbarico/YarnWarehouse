package dev.tarico.services;

public class URLParser {
    public int extractId(String url) {
        String[] temp = url.split("/");
        return Integer.parseInt(temp[1]);
    }
}
