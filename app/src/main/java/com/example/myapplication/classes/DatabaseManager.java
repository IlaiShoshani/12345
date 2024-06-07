package com.example.myapplication.classes;
import static com.example.myapplication.QuestionActivity.setQuestion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.util.Map;
import java.util.Random;

public class DatabaseManager {
    private static  FirebaseFirestore db;
    static public String largestId;
    public static Random rnd;
    public static void loadQuestion(String id)
    {
        db.collection("Question")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Integer.parseInt(document.getId()) == Integer.parseInt(id))
                                {
                                    Map<String,Object> data = document.getData();
                                    Question question = new Question((String)data.get("question"),(String)data.get("answer1"), (String)data.get("answer2"), (String)data.get("answer3"), (String)data.get("answer4"), 10);
                                    setQuestion(question);
                                }
                                Log.d("nave", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("nave", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void uploadQuestion(Question question)
    {
        int id = Integer.parseInt(largestId);
        String customId = Integer.toString(id+1);
        db.collection("Question").document(customId).set(question)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("nave", "DocumentSnapshot added with ID: " + customId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("nave", "Error adding document", e);
                    }
                });
    }
    public void getLargestIdAndThenUpload(Question question) {
        db.collection("Question")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(Integer.parseInt(document.getId()) > Integer.parseInt(largestId)) {
                                    largestId = document.getId();
                                }
                                Log.d("nave", document.getId() + " => " + document.getData());
                            }
                            uploadQuestion(question);
                        } else {
                            Log.w("nave", "Error getting documents.", task.getException());
                        }
                    }

                });
    }

    public static int getLargestIdAndGenerateRandomQuestion() {
        db.collection("Question")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(Integer.parseInt(document.getId()) > Integer.parseInt(largestId)) {
                                    largestId = document.getId();
                                }
                                loadQuestion(Integer.toString(rnd.nextInt(Integer.parseInt(largestId))));
                                Log.d("nave", document.getId() + " => " + document.getData());

                            }
                        } else {
                            Log.w("nave", "Error getting documents.", task.getException());
                        }
                    }

                });
        return Integer.parseInt(largestId);

    }
    public DatabaseManager()
    {
        db = FirebaseFirestore.getInstance();
        largestId = "0";
    }
}
