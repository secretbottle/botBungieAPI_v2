package net.bungie.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class BungieApi {
    private final static String ROOT_PATH = "https://www.bungie.net/Platform/Destiny2/";

    @Value("${X-API-KEY}")
    private static String X_API_KEY;

    //private final static String X_API_KEY = System.getenv("X_API_KEY");

    public static String request(String path) {
        try {
            URL url = new URL(ROOT_PATH + path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-API-KEY", X_API_KEY);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                return content.toString();
            } catch (IOException e) {
                log.error("Error while reading data from Bungie.net API:", e);
                return null;
            }

        } catch (IOException e) {
            log.error("Error to Bungie.net API: ", e);
            return null;
        }
    }
}
