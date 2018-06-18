package net.model;

public class examsPerSecuencia {
    private String idsecuencia;
    private int idexamen;

    public examsPerSecuencia() {
    }

    public examsPerSecuencia(String idsecuencia, int idexamen) {
        this.idsecuencia = idsecuencia;
        this.idexamen = idexamen;
    }

    public String getIdsecuencia() {
        return idsecuencia;
    }

    public void setIdsecuencia(String idsecuencia) {
        this.idsecuencia = idsecuencia;
    }

    public int getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(int idexamen) {
        this.idexamen = idexamen;
    }
}
