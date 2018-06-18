package net.model;

import java.util.List;

public class exam {
    private int id;
    private String title;
    private String image;
    private String description;
    private int questions;
    private String creatDate;
    private String dischargeDate;
    private float note;
    private int id_user;
    private int id_status;
    private int id_typeExa;
    private List<questionPerExam> questionsList;
    
    public exam() {
    }

    public exam(int id) {
        this.id = id;
    }

    public exam(int id, String title, String description, int questions, String creatDate, String dischargeDate, 
            float note, int id_user, int id_status, int id_typeExa, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
        this.creatDate = creatDate;
        this.dischargeDate = dischargeDate;
        this.note = note;
        this.id_user = id_user;
        this.id_status = id_status;
        this.id_typeExa = id_typeExa;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_typeExa() {
        return id_typeExa;
    }

    public void setId_typeExa(int id_typeExa) {
        this.id_typeExa = id_typeExa;
    }

    public List<questionPerExam> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<questionPerExam> questionsList) {
        this.questionsList = questionsList;
    }
}
