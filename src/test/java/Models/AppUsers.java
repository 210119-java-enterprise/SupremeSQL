package Models;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.SerialNumber;
import annotations.Table;

import java.util.Objects;

@Table(name = "app_user")
public class AppUsers {

    @PrimaryKey(name = "user_id") @SerialNumber
    private int id;

    @Column(column = "firstname")
    private String firstname;

    @Column(column = "lastname")
    private String lastname;

    @Column(column = "username")
    private String username;

    @Column(column = "password")
    private String password;

    public AppUsers(){
        super();
    }

    public AppUsers(String username) {
        this.username = username;
    }

    public AppUsers(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public AppUsers(int id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public AppUsers(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUsers appUsers = (AppUsers) o;
        return id == appUsers.id && Objects.equals(firstname, appUsers.firstname) && Objects.equals(lastname, appUsers.lastname) && Objects.equals(username, appUsers.username) && Objects.equals(password, appUsers.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, username, password);
    }

//    @Override
//    public String toString() {
//
//
//
//        if(id == 0 && username == null && password == null){
//            return   " Firstname = " + firstname + ' ' +
//                    " Lastname = " + lastname ;
//        }
//        if(id == 0 && password == null){
//            return   " Firstname = " + firstname + ' ' +
//                    " Lastname = " + lastname + ' ' +
//                    " Username = " + username;
//        }
//
//        if(id == 0){
//            return   " firstname = " + firstname + ' ' +
//                    " lastname = " + lastname + ' ' +
//                    " username = " + username + ' ' +
//                    " password = " + password + ' ';
//        }
//
//        return " ID = " + id +
//                " Firstname= " + firstname + ' ' +
//                " Lastname= " + lastname + ' ' +
//                " Username= " + username + ' ' +
//                " Password= " + password ;
//    }

    @Override
    public String toString() {
        return "AppUsers{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
