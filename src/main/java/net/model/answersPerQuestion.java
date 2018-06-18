package net.model;

public class answersPerQuestion {
    private int id;
    private int idQuestion;
    private String answer;
    private int status;
    private Boolean isEditable;

    public answersPerQuestion() {
    }

    public answersPerQuestion(int id, int idQuestion, String answer, int status, Boolean edit) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.answer = answer;
        this.status = status;
        this.isEditable = edit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }
    
}