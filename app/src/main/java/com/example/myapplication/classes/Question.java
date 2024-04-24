package com.example.myapplication.classes;

public class Question {//i tried using a array for the answers but firestore caused the app to crash
    private String question;
    private int rating;//this variable rates how good the question is and based on that shows it
    //to more or less users
    private String answer1;//answer 1 is always correct
    private String answer2;
    private String answer3;
    private String answer4;
    public Question(String q, String a, String b, String c, String d, int r)
    {
        question = q;
        answer1 = a;
        answer2 = b;
        answer3 = c;
        answer4 = d;
        rating = r;
    }
    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public String getAnswer3() {
        return answer3;
    }
    public String getAnswer4() {
        return answer4;
    }
    public int getRating() {
        return rating;
    }

}
