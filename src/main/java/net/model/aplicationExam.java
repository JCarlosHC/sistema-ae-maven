package net.model;

import java.util.List;

public class aplicationExam {
    private int idExam;
    private String idStudent;
    private float note;
    private float noteExam;
    private int numQuestions;
    private int numQuestionsOK;
    private String startDate;
    private String endDate;
    private List<detailExam> details;

    public aplicationExam() {
    }

    public aplicationExam(int idExam, String idStudent, float note, String startDate, String endDate, List<detailExam> details) {
        this.idExam = idExam;
        this.idStudent = idStudent;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
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

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<detailExam> getDetails() {
        return details;
    }

    public void setDetails(List<detailExam> details) {
        this.details = details;
    }

    public float getNoteExam() {
        return noteExam;
    }

    public void setNoteExam(float noteExam) {
        this.noteExam = noteExam;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getNumQuestionsOK() {
        return numQuestionsOK;
    }

    public void setNumQuestionsOK(int numQuestionsOK) {
        this.numQuestionsOK = numQuestionsOK;
    }
    
}
