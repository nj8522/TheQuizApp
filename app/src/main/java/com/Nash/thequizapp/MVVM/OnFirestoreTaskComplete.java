package com.Nash.thequizapp.MVVM;

import java.util.List;

public interface OnFirestoreTaskComplete {

      void firebaseDataAdded(List<Modal> data);
      void onError(Exception e);

}
