package main.by.htp.ts.bean;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 3389934289914158830L;

    private int id;
    private String name;
    private String description;

    public Question(){}

    public Question(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
