package com.Nash.thequizapp.DB;

import com.google.firebase.firestore.DocumentId;

public class GameModal {


    @DocumentId
    String gameId;

    String option_a;
    String option_b;
    String option_c;
    String question;
    String answer;
    Long timer;

    public GameModal(){}

    public GameModal(String gameId, String option_a, String option_b, String option_c, String question, String answer, Long timer) {
        this.gameId = gameId;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.question = question;
        this.answer = answer;
        this.timer = timer;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}
