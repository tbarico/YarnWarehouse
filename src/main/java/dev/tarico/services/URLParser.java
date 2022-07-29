package dev.tarico.services;

/**
 * Helper class that parses the URL.
 * 
 * @author Tara Arico 7.29.2022
 */
public class URLParser {
    /**
     * Parses the given url for the first int.
     * 
     * @param url - url to parse.
     * @return first int value of the url
     */
    public int extractId(String url) {
        String[] temp = url.split("/");
        return Integer.parseInt(temp[1]);
    }
}
