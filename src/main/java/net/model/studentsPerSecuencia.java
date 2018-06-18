package net.model;

public class studentsPerSecuencia {
    private String idstudent;
    private String idsecuencia;
    private int idstatus;

    public studentsPerSecuencia() {
    }

    public studentsPerSecuencia(String idstudent, String idsecuencia, int idstatus) {
        this.idstudent = idstudent;
        this.idsecuencia = idsecuencia;
        this.idstatus = idstatus;
    }

    public String getIdstudent() {
        return idstudent;
    }

    public void setId(String idstudent) {
        this.idstudent = idstudent;
    }

    public String getIdsecuencia() {
        return idsecuencia;
    }

    public void setIdsecuencia(String idsecuencia) {
        this.idsecuencia = idsecuencia;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }
}
