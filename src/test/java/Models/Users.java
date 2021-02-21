package Models;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.SerialNumber;
import annotations.Table;

import java.util.Objects;

@Table(name = "users" )
public class Users {

    @PrimaryKey(name = "user_id") @SerialNumber
    private int id;

    @Column(column = "firstname")
    private String firstname;

    @Column(column = "lastname")
    private String lastname;

    public Users(){
        super();
    }

    public Users(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Users(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && Objects.equals(firstname, users.firstname) && Objects.equals(lastname, users.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname);
    }
}
