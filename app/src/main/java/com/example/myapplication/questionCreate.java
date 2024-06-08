package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.classes.DatabaseManager;
import com.example.myapplication.classes.Question;
import com.example.myapplication.classes.QuestionFetcher;

import java.io.IOException;

public class questionCreate extends AppCompatActivity implements QuestionFetcher.QuestionFetchListener {

    private Button submit;
    private Button random;
    private Button exit;
    private EditText question;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        submit = findViewById(R.id.buttonSubmitQuestion);
        exit = findViewById(R.id.buttonClose);
        question = findViewById(R.id.editTextQuestion);
        answer1 = findViewById(R.id.editTextAnswer1);
        answer2 = findViewById(R.id.editTextAnswer2);
        answer3 = findViewById(R.id.editTextAnswer3);
        answer4 = findViewById(R.id.editTextAnswer4);
        random = findViewById(R.id.apiButton);
        manager = new DatabaseManager();

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Question questionObject = new Question(question.getText().toString(), answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString(), 10);
                manager.getLargestIdAndThenUpload(questionObject);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        random.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                QuestionFetcher.fetchQuestion(new QuestionFetcher.QuestionFetchListener() {
                    @Override
                    public void onQuestionFetched(Question question1) {
                        question.setText(question1.getQuestion());
                        answer1.setText(question1.getAnswer1());
                        answer2.setText(question1.getAnswer2());
                        answer3.setText(question1.getAnswer3());
                        answer4.setText(question1.getAnswer4());
                    }
                });

            }
        });
    }

    @Override
    public void onQuestionFetched(Question question1) {
        question.setText(question1.getQuestion());
        answer1.setText(question1.getAnswer1());
        answer2.setText(question1.getAnswer2());
        answer3.setText(question1.getAnswer3());
        answer4.setText(question1.getAnswer4());
    }
}