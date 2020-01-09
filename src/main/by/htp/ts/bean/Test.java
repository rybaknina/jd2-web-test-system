package main.by.htp.ts.bean;


import java.io.Serializable;
import java.sql.Time;

public class Test implements Serializable {
    private static final long serialVersionUID = 3389934289914158830L;

    private int id;
    private String name;
    private Time time;
    private String status;

    public Test() {
    }

    public Test(String name, Time time) {
        this.name = name;
        this.time = time;
        this.status = "0";
    }

    public Test(String name, Time time, String status) {
        this.name = name;
        this.time = time;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
