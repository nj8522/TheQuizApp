package com.Nash.thequizapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Nash.thequizapp.MVVM.Modal;
import com.Nash.thequizapp.MVVM.ViewModal;
import com.Nash.thequizapp.R;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    //View Modal
    ViewModal viewModal;

    //Element Position
    int position;

    //UI elements
    ImageView detail_img;
    TextView detail_title;
    TextView detail_desc;
    TextView detail_level;
    TextView detail_questions;
    Button detail_btn;

    //Navigation
    NavController navController;

    //DB elements
    String quizId;
    Long questions;

    public DetailFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        position = DetailFragmentArgs.fromBundle(getArguments()).getPosition();
        Log.i("Detail","Position :"+ position);

        //UI elements
        detail_img = view.findViewById(R.id.detail_image);
        detail_title = view.findViewById(R.id.detail_title);
        detail_desc = view.findViewById(R.id.detail_desc);
        detail_level = view.findViewById(R.id.detail_level);
        detail_questions = view.findViewById(R.id.detail_questions);
        detail_btn = view.findViewById(R.id.detail_btn);

        detail_btn.setOnClickListener(this);

        navController = Navigation.findNavController(view);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModal = new ViewModelProvider(getActivity()).get(ViewModal.class);
        viewModal.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Modal>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Modal> modalList) {

                //Img
                String img = modalList.get(position).getImg();
                Glide.with(getContext())
                        .load(img)
                        .centerCrop()
                        .placeholder(R.color.splash_color)
                        .into(detail_img);

                detail_title.setText(modalList.get(position).getName());
                detail_desc.setText(modalList.get(position).getDesc());
                detail_level.setText(modalList.get(position).getLevel());
                detail_questions.setText(modalList.get(position).getQuestion().toString());

                quizId = modalList.get(position).getDocId();
                questions = modalList.get(position).getQuestion();
            }
        });


    }

    @Override
    public void onClick(View v) {
        DetailFragmentDirections.ActionDetailFragmentToGameFragment action = DetailFragmentDirections.actionDetailFragmentToGameFragment();
        action.setQuizId(quizId);
        action.setQuestions(questions);
        navController.navigate(action);
    }
}
