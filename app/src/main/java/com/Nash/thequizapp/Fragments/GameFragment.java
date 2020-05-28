package com.Nash.thequizapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Nash.thequizapp.DB.GameModal;
import com.Nash.thequizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    String quizId;

    //Firebase
    FirebaseFirestore firebaseFirestore;
    FirebaseUser currentUser;

    //List
    List<GameModal> dataList = new ArrayList<>();
    List<GameModal> selectedQuestion = new ArrayList<>();
    Long totalQuestion = 0L;

    //UI elements
    TextView questionText;
    TextView timer;
    Button option_a;
    Button option_b;
    Button option_c;
    Button next;
    Button close;
    Button gameResult;

    //CountDown Timer
    private CountDownTimer countDownTimer;

    //Verification
    String answerKey;
    int pos = 0;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    int noAnswer = 0;

    //Navigation
    NavController navController;

    public GameFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quizId = GameFragmentArgs.fromBundle(getArguments()).getQuizId();
        Log.i("quiz", "Quiz ID :"+ quizId);
        totalQuestion = GameFragmentArgs.fromBundle(getArguments()).getQuestions();

        //Firebase
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Navigation
        navController = Navigation.findNavController(view);

        //UI elements
        questionText = view.findViewById(R.id.game_question);
        timer = view.findViewById(R.id.game_time);
        option_a = view.findViewById(R.id.game_option_a);
        option_b = view.findViewById(R.id.game_option_b);
        option_c = view.findViewById(R.id.game_option_c);
        next = view.findViewById(R.id.game_next_btn);
        close = view.findViewById(R.id.game_close_btn);
        gameResult = view.findViewById(R.id.game_results);

        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);

        gameResult.setVisibility(View.INVISIBLE);
        gameResult.setEnabled(false);

        option_a.setOnClickListener(this);
        option_b.setOnClickListener(this);
        option_c.setOnClickListener(this);

        next.setOnClickListener(this);
        gameResult.setOnClickListener(this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });

        getGameQuestion();



    }

    private void loadUI(){
        startGame(pos);
    }

    private void startGame(int pos) {
        questionText.setText(selectedQuestion.get(pos).getQuestion());
        option_a.setText(selectedQuestion.get(pos).getOption_a());
        option_b.setText(selectedQuestion.get(pos).getOption_b());
        option_c.setText(selectedQuestion.get(pos).getOption_c());
        answerKey = selectedQuestion.get(pos).getAnswer();
        Log.i("Verify","Answer :" + answerKey);

        loadTimer(pos);

    }

    private void loadTimer(int questNumber) {

        //timer
        final Long countDown = selectedQuestion.get(questNumber).getTimer();
        timer.setText(countDown.toString());

        countDownTimer = new CountDownTimer(countDown*1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                 timer.setText(millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {

                noAnswer++;
                Log.i("answer", "UnAnswered :" + noAnswer);
                showButton();
            }
        };

            countDownTimer.start();
    }

    private void getGameQuestion() {

        firebaseFirestore.collection("quiz")
                         .document(quizId)
                         .collection("questions")
                         .get()
                          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                              @Override
                              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                   if(task.isSuccessful()){

                                       dataList =  task.getResult().toObjects(GameModal.class);
                                       displayQuestion();
                                       loadUI();
                                   }else {
                                       Exception e = task.getException();
                                       Log.e("Data Error","Data :"+ e.toString());
                                   }
                              }
                          });
            }

            private void displayQuestion() {

            for(int i = 0; i < totalQuestion; i++){

            int randomNumber = generateRandom(0, dataList.size());
            Log.i("Game","Random  :" + randomNumber);
            selectedQuestion.add(dataList.get(randomNumber));
            dataList.remove(randomNumber);
            Log.i("Game","Question :" + selectedQuestion.get(i).getQuestion());
        }
            Log.i("Game","Random  :" + selectedQuestion.size());
    }

    private int generateRandom(int min, int max){
        return ((int) (Math.random()*(max-min))) + min;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                    case R.id.game_option_a :
                    verifyOption(option_a);
                    break;
                    case R.id.game_option_b :
                    verifyOption(option_b);
                    break;
                    case R.id.game_option_c :
                    verifyOption(option_c);
                    break;
                    case R.id.game_next_btn :
                    ++pos;
                    startGame(pos);
                    nextQuestion();
                    break;
                    case R.id.game_results :
                    getResult();
                    break;
        }

    }

    private void nextQuestion() {

        option_a.setBackground(getResources().getDrawable(R.color.colorAccent, null));
        option_b.setBackground(getResources().getDrawable(R.color.colorAccent, null));
        option_c.setBackground(getResources().getDrawable(R.color.colorAccent, null));

        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
    }


    private void verifyOption(Button options) {

            if(answerKey.trim().equalsIgnoreCase(options.getText().toString().trim())){

                correctAnswer++;
                Log.i("Verify","true");
                countDownTimer.cancel();
                countDownTimer.onFinish();
                Log.i("answer","Correct :" + correctAnswer);
                options.setBackground(getResources().getDrawable(R.drawable.correct_btn_bg, null));


            }else{
                wrongAnswer++;
                Log.i("Verify","false");
                options.setBackground(getResources().getDrawable(R.drawable.wrong_btn_bg, null));
                Log.i("answer", "Wrong :" +  wrongAnswer);
                countDownTimer.cancel();
                countDownTimer.onFinish();
            }

            showButton();
    }

    private void showButton() {
         if(pos == totalQuestion -1){
             gameResult.setVisibility(View.VISIBLE);
             gameResult.setEnabled(true);
         }else {

             next.setEnabled(true);
             next.setVisibility(View.VISIBLE);
         }
    }

    private void getResult(){

        HashMap<String, Object> gameData = new HashMap<>();
        gameData.put("Correct", correctAnswer);
        gameData.put("Wrong", wrongAnswer);
        gameData.put("UnAnswered", noAnswer);

        firebaseFirestore.collection("quiz")
                .document(quizId)
                .collection("result")
                .document()
                .set(gameData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            GameFragmentDirections.ActionGameFragmentToResultFragment action = GameFragmentDirections.actionGameFragmentToResultFragment();
                            action.setCorrect(correctAnswer);
                            action.setWrong(wrongAnswer);
                            navController.navigate(action);
                        }else{
                            Exception e = task.getException();
                            Log.e("Upload","Error : "+ e);
                        }
                    }
                });
    }

    private void quitGame() {

        countDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are Sure You want to Quit");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  navController.navigate(R.id.action_gameFragment_to_homeFragment);
            }
        });
       builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
               countDownTimer.start();
           }
       });

       AlertDialog dialog = builder.create();
       dialog.show();

    }

}
