package net.model;

public class detailExam {
    private int idExam;
    private String idStudent;
    private int idQuestion;
    private int idAnswer;
    private boolean isCorrect;

    public detailExam() {
    }

    public detailExam(int idExam, String idStudent, int idQuestion, int idAnswer, boolean isCorrect) {
        this.idExam = idExam;
        this.idStudent = idStudent;
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
        this.isCorrect = isCorrect;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(int idExam) {
        this.idExam = idExam;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
    
}
