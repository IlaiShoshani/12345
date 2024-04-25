package com.example.myapplication.classes;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.classes.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

public class DatabaseManager {
    private FirebaseFirestore db;
    static String largestId;
    public Question loadQuestion(String id)
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
                                    Object data = document.getData();
                                }
                                Log.d("nave", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("nave", "Error getting documents.", task.getException());
                        }
                    }
                });
        return null;

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
                            largestId = "0";
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
    public DatabaseManager()
    {
        db = FirebaseFirestore.getInstance();
    }
}
