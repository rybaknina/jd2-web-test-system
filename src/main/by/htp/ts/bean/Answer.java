package main.by.htp.ts.bean;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 3389934289914158830L;

    private int id;
    private int number;
    private String name;
    private int point;
    private int question_id;

    public Answer(){}

    public Answer(int number, String name, int point, int question_id) {
        this.number = number;
        this.name = name;
        this.point = point;
        this.question_id = question_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
