package com.Nash.thequizapp.MVVM;

import com.google.firebase.firestore.DocumentId;

public class Modal {

    @DocumentId
    String docId;

    String desc;
    String name;
    String img;
    String level;
    Long question;

    public Modal(){}

    public Modal(String docId, String desc, String name, String img, String level, Long question) {
        this.docId = docId;
        this.desc = desc;
        this.name = name;
        this.img = img;
        this.level = level;
        this.question = question;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }
}
