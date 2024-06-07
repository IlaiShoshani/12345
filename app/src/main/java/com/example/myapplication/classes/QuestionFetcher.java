package com.example.myapplication.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.example.myapplication.questionCreate;
import com.google.gson.Gson;

public class QuestionFetcher {

    private static final String API_URL = "https://opentdb.com/api.php?amount=1&type=multiple";
    private static final Gson gson = new Gson();

    public static Question fetchQuestion() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000); // Optional: Set connection timeout in milliseconds

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch question: Status code " + connection.getResponseCode());
        }

        StringBuilder responseBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
        }

        String responseBody = responseBuilder.toString();

        // Parse JSON using Gson
        TriviaResponse triviaResponse = gson.fromJson(responseBody, TriviaResponse.class);
        List<Result> results = triviaResponse.getResults();

        if (results.isEmpty()) {
            throw new IOException("No questions found in response");
        }

        Result result = results.get(0);
        String question = result.getQuestion();

        // Randomize the order of answers (including the correct answer)
        List<String> answers = result.getIncorrectAnswers();
        answers.add(result.getCorrectAnswer());
        Collections.shuffle(answers);

        return new Question(
                question,
                answers.get(0), // First answer becomes answer1 (may or may not be correct)
                answers.get(1),
                answers.get(2),
                answers.get(3),
                0 // Set rating to default value (0)
        );
    }

    private static class TriviaResponse {
        private int response_code;
        private List<Result> results;

        public int getResponse_code() {
            return response_code;
        }

        public List<Result> getResults() {
            return results;
        }
    }

    private static class Result {
        private String type;
        private String difficulty;
        private String category;
        private String question;
        private String correct_answer;
        private List<String> incorrect_answers;

        public String getType() {
            return type;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public String getCategory() {
            return category;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrectAnswer() {
            return correct_answer;
        }

        public List<String> getIncorrectAnswers() {
            return incorrect_answers;
        }
    }

}