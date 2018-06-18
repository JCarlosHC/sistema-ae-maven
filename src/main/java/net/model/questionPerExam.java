package net.model;

import java.util.List;

public class questionPerExam {
    private int id;
    private String question;
    private String creatDate;
    private String unitTemary;
    private String dischargeDate;
    private int idStatus;
    private int idUser;
    private List<answersPerQuestion> answers;

    public questionPerExam() {
    }

    public questionPerExam(int id, String question, String creatDate, String unitTemary, String dischargeDate, int idStatus, int idUser) {
        this.id = id;
        this.question = question;
        this.creatDate = creatDate;
        this.unitTemary = unitTemary;
        this.dischargeDate = dischargeDate;
        this.idStatus = idStatus;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getUnitTemary() {
        return unitTemary;
    }

    public void setUnitTemary(String unitTemary) {
        this.unitTemary = unitTemary;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<answersPerQuestion> getAnswer() {
        return answers;
    }

    public void setAnswers(List<answersPerQuestion> answers) {
        this.answers = answers;
    }
    
}
