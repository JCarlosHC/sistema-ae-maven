package net.model;

public class user {
    private int id;
    private String firstname;
    private String psurname;
    private String msurname;
    private String email;
    private String phone;
    private String password;
    private int id_tipo;
    private int id_status;

    public user() {
    }

    public user(int id, String firstname, String psurname, String msurname, String email, String phone, int id_tipo, int id_status) {
        this.id = id;
        this.firstname = firstname;
        this.psurname = psurname;
        this.msurname = msurname;
        this.email = email;
        this.phone = phone;
        this.id_tipo = id_tipo;
        this.id_status = id_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPsurname() {
        return psurname;
    }

    public void setPsurname(String psurname) {
        this.psurname = psurname;
    }

    public String getMsurname() {
        return msurname;
    }

    public void setMsurname(String msurname) {
        this.msurname = msurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }
    
    
}
