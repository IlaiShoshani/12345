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
    /*public Question loadQuestion(String id)
    {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId() == id)
                                {

                                }
                                Log.d("nave", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("nave", "Error getting documents.", task.getException());
                        }
                    }
                });


    }*/
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
                .orderBy("id", Query.Direction.DESCENDING) // Assuming "id" is the field representing the ID
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            largestId = documentSnapshot.getString("id");
                            Log.d("nave", "Largest ID: " + largestId);
                        } else {
                            Log.d("nave", "No documents found");
                            largestId = "0";

                        }
                        uploadQuestion(question);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("nave", "Error getting documents", e);
                    }
                });
    }
    public DatabaseManager()
    {
        db = FirebaseFirestore.getInstance();
    }
}
