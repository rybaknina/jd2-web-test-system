package main.by.htp.ts.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Role implements Serializable {

    private static final long serialVersionUID = 6378569468706684976L;
    private int id;
    private String title;
    private List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id &&
                title.equals(role.title) &&
                Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, users);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", users=" + users +
                '}';
    }
}
