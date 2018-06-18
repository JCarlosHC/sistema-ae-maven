package net.model;

public class school {
    
    private int id;
    private String description;
    private String acronym;
    
    public school(){}
    
    public school(int id, String description, String acronym) {
        this.id = id;
        this.description = description;
        this.acronym = acronym;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    
   
}
