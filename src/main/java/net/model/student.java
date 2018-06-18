package net.model;

public class student {
    private String id;
    private String firstname;
    private String psurname;
    private String msurname;
    private String email;
    private String phone;
    private String password;
    private int id_planest;
    private int id_school;
    private int id_career;
    private int id_status;

    public student() {
    }

    public student(String id, String firstname, String psurname, String msurname, String email, String phone, int id_planest, int id_school, int id_career, int id_status) {
        this.id = id;
        this.firstname = firstname;
        this.psurname = psurname;
        this.msurname = msurname;
        this.email = email;
        this.phone = phone;
        this.id_planest = id_planest;
        this.id_school = id_school;
        this.id_career = id_career;
        this.id_status = id_status;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getId_planest() {
        return id_planest;
    }

    public void setId_planest(int id_planest) {
        this.id_planest = id_planest;
    }

    public int getId_school() {
        return id_school;
    }

    public void setId_school(int id_school) {
        this.id_school = id_school;
    }

    public int getId_career() {
        return id_career;
    }

    public void setId_career(int id_career) {
        this.id_career = id_career;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }
    
}
