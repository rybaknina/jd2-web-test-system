package main.by.htp.ts.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 3389934289914158830L;

    private int id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private java.sql.Date birthday;
    private int role_id;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, int role_id) {
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    public User(String email, String password, String name, String lastname, java.sql.Date birthday, int role_id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                role_id == user.role_id &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role_id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                '}';
    }
}
