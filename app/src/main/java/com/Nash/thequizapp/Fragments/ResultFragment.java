package com.Nash.thequizapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.Nash.thequizapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    Button home;
    TextView correctAnswer;
    int correct;

    //Navigation
    NavController navController;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       correct = ResultFragmentArgs.fromBundle(getArguments()).getCorrect();
       int wrong  = ResultFragmentArgs.fromBundle(getArguments()).getWrong();

       home = view.findViewById(R.id.result_home);
       correctAnswer = view.findViewById(R.id.result_correct);

       navController = Navigation.findNavController(view);

       Log.i("Result","correct : "+ correct + "wrong :"+ wrong);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        correctAnswer.setText(String.valueOf(correct));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resultFragment_to_homeFragment);
            }
        });


    }
}
