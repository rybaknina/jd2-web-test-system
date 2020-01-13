package main.by.htp.ts.bean;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 3389934289914158830L;

    private int id;
    private String text;
    private int testId;

    public Question(){}

    public Question(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }
    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
