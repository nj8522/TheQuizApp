package com.Nash.thequizapp.MVVM;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference ref =firebaseFirestore.collection("quiz");

    //Interface
    OnFirestoreTaskComplete onFirestoreTaskComplete;
    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete){ this.onFirestoreTaskComplete = onFirestoreTaskComplete;}

    public void getData(){

        ref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if(task.isSuccessful()){
                             Log.i("retirve","Success");
                             onFirestoreTaskComplete.firebaseDataAdded(task.getResult().toObjects(Modal.class));
                         }else{
                             Log.e("Retirve","Fail");
                             onFirestoreTaskComplete.onError(task.getException());
                         }
                    }
                });

    }


}
