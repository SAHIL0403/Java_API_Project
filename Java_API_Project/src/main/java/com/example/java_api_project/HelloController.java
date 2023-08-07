package com.example.java_api_project;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HelloController {

    // FXML element reference for the ListView to display news headlines
    @FXML
    private ListView<String> newsListView;

    // Method called when the FXML is loaded
    public void initialize() {
        // API URL and key for news retrieval
        String apiKey = "1f6a9f4182e44b998953aef347cdfc63";
        String apiUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;

        try {
            // Create an HTTP connection and request for the API data
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Get the response code and check if the request is successful (response code 200)
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                // Read the response data and store it in a StringBuilder
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response and get the news headlines using Gson
                Gson gson = new Gson();
                NewsResponse newsResponse = gson.fromJson(response.toString(), NewsResponse.class);
                List<String> headlines = new ArrayList<>();

                // Extract article titles from the JSON response and add them to the headlines list
                for (Article article : newsResponse.articles) {
                    headlines.add(article.title);
                }

                // Populate the ListView with the news headlines
                newsListView.getItems().addAll(headlines);
            } else {
                System.out.println("API Request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner classes for JSON mapping
    public static class NewsResponse {
        public List<Article> articles;
    }

    public static class Article {
        public String title;
    }
}
