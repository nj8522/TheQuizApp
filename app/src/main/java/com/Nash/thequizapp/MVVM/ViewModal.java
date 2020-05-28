package com.Nash.thequizapp.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ViewModal extends ViewModel implements OnFirestoreTaskComplete {

   private MutableLiveData<List<Modal>> mutableLiveData = new MutableLiveData<>();

    public LiveData<List<Modal>> getMutableLiveData() {
        return mutableLiveData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public ViewModal(){
        firebaseRepository.getData();
    }



    @Override
    public void firebaseDataAdded(List<Modal> data) {
              mutableLiveData.setValue(data);
    }

    @Override
    public void onError(Exception e) {

    }
}
