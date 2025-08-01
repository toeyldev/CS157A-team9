package com.example.osintme;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HIBPService {
    private final String apiKey;
    private final String userAgent;

    public HIBPService(ServletContext ctx) {
        this.apiKey    = ctx.getInitParameter("hibpApiKey");
        this.userAgent = ctx.getInitParameter("hibpUserAgent");
    }

    public HIBPService(String apiKey, String userAgent) {
        this.apiKey    = apiKey;
        this.userAgent = userAgent;
    }

    // Call HIBP API for a given email
    // Return JSON
    public String fetchBreaches(String email) {
        try {
            String encodedEmail = URLEncoder.encode(email, "UTF-8");

            String endpoint = "https://haveibeenpwned.com/api/v3/breachedaccount/" + encodedEmail + "?truncateResponse=false";
            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("hibp-api-key", apiKey);
            conn.setRequestProperty("User-Agent", userAgent);

            int code = conn.getResponseCode();
            if (code == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                return sb.toString();
            }
            // Breach not found
            return code == 404 ? "[]" : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
