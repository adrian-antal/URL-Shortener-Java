import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class URLShortener {
    // Default domain name
    private final String defaultURL;
    // Chars used to generate shortURL
    private final char charToNum[];
    // Storage for keys
    // shortURL keys
    private Map<String, String> shortKeyMap;
    // longURL keys
    private Map<String, String> longKeyMap;

    public URLShortener() {
        this.defaultURL = "short.ly/";
        this.shortKeyMap = new HashMap<>();
        this.longKeyMap = new HashMap<>();
        this.charToNum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }

    // Method to generate short URL
    public String urlShortener(String longURL) {
        // For demonstration, check for invalid URL first
        if(!validateURL(longURL)){
            return "Invalid URL";
        }
        String shortURL = "";
        if(longKeyMap.containsKey(longURL)){
            shortURL = defaultURL + longKeyMap.get(longURL);
        } else {
            shortURL = defaultURL + getKey(longURL);
        }
        return shortURL;
    }

    // Method that returns original URL from provided shortURL
    public String expandURL(String shortURL) {
        String key = shortURL.substring(defaultURL.length());
        return shortKeyMap.get(key);
    }

    // Method to generate key for shortURL and add them to hashmaps along with original URL
    private String getKey(String longURL){
        String key;
        do {
            key = "";
            // Generate 9 random characters from an array to create a key
            for(int i = 0; i <= 8; i++){
                int random = (int )(Math.random() * 62);
                key += charToNum[random];
            }
            if(!shortKeyMap.containsKey(key)){
                break;
            }
        } while (true);
        shortKeyMap.put(key, longURL);
        longKeyMap.put(longURL, key);
        return key;
    }

    // Method to validate URL
    private boolean validateURL(String url){
        try {
            // URL object to check the format of the URL
            URL isValidURL = new URL(url);
            // URLConnection object to check whether connection can be made
            URLConnection connection = isValidURL.openConnection();
            connection.connect();
        } catch (MalformedURLException e) {
            // Invalid URL
            return false;
        } catch (IOException e) {
            // Connection can't be established
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // Test URLShortener
        URLShortener shortener = new URLShortener();

        // 2nd and 3rd URL should display same shortURL and last URL is invalid
        String[] urls = {"https://www.codewars.com/kata/5fee4559135609002c1a1841", "https://www.amazon.com", "https://www.amazon.com", "https://jwww.amazon.com"};

        for(int i = 0; i < urls.length; i++){
            System.out.println("\nOriginal URL: " + urls[i] +
                    "\nShort URL: " + shortener.urlShortener(urls[i]) +
                    "\nExpanded URL: " + shortener.expandURL(shortener.urlShortener(urls[i])));
        }
    }
}