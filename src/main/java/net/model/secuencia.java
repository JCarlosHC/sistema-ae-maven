package net.model;

public class secuencia {
    private String id;
    private String idsubject;
    private int idstatus;
    private int iduser;
    private String descripcion;
    private int totalstudents;
    
    public secuencia() {
    }

    public secuencia(String id, String idsubject, int idstatus, int iduser, String descripcion) {
        this.id = id;
        this.idsubject = idsubject;
        this.idstatus = idstatus;
        this.iduser = iduser;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getIdsubject() {
        return idsubject;
    }

    public void setIdsubject(String idsubject) {
        this.idsubject = idsubject;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTotalstudents() {
        return totalstudents;
    }

    public void setTotalstudents(int totalstudents) {
        this.totalstudents = totalstudents;
    }
    
}
