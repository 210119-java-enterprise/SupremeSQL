package Models;

import annotations.Column;
import annotations.PrimaryKey;
import annotations.Table;

@Table(name = "account")
public class Account {

    @PrimaryKey(name = "account_id")
    private int id;

    @Column(column = "balance")
    private int balance;

    @Column(column = "user_id")
    private int userID;

    public Account(int id, int balance, int userID) {
        this.id = id;
        this.balance = balance;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
