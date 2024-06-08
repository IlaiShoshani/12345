package com.example.myapplication;

import static com.example.myapplication.classes.DatabaseManager.getLargestIdAndGenerateRandomQuestion;
import static com.example.myapplication.classes.DatabaseManager.largestId;
import static com.example.myapplication.classes.DatabaseManager.loadQuestion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

import com.example.myapplication.classes.DatabaseManager;
import com.example.myapplication.classes.DatabaseManager.*;
import com.example.myapplication.classes.Question;
import com.example.myapplication.classes.utils;

public class QuestionActivity extends AppCompatActivity {
    private static int correntAnswers;
    private static Question lastQuestion;
    private static TextView QuestionText;
    private static RadioButton[] radioButtons;
    private static ArrayList<String> answers;
    private static String checkedAnswer;
    private static DatabaseManager manager;
    private static Button button;
    private static TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        QuestionText = findViewById(R.id.questionText);
        score = findViewById(R.id.score);
        radioButtons = new RadioButton[]{findViewById(R.id.radioButton), findViewById(R.id.radioButton2), findViewById(R.id.radioButton3), findViewById(R.id.radioButton4)};
        answers = new ArrayList<>();
        manager = new DatabaseManager();
        DatabaseManager.rnd = new Random();
        getLargestIdAndGenerateRandomQuestion();
        button = (Button)findViewById(R.id.button5);
        correntAnswers = 0;
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            checkedAnswer = " ";
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                checkedAnswer = radioButton.getText().toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextQuestion();
            }

        });
    }
    public static void setQuestion(Question question)
    {
        int lastRandom;
        Random rand = new Random();
        lastQuestion = question;
        QuestionText.setText(question.getQuestion());
        answers.add(question.getAnswer1());
        answers.add(question.getAnswer2());
        answers.add(question.getAnswer3());
        answers.add(question.getAnswer4());

        int sizeOfArray = radioButtons.length;
        for(int i =sizeOfArray-1; i>=0 ; i--)
        {
            lastRandom =rand.nextInt(answers.size());
            radioButtons[i].setText(answers.get(lastRandom));
            answers.removeAll(Collections.singleton(answers.get(lastRandom)));
        }
    }
    public static void nextQuestion()
    {
        if (Objects.equals(checkedAnswer, lastQuestion.getAnswer1()))
        {
            correntAnswers++;
            score.setText(Integer.toString(correntAnswers*100));
        }
        getLargestIdAndGenerateRandomQuestion();
    }

}