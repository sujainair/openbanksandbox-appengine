package com.appengine.openbank;

/**
 * Created by sujai on 23-06-2016.
 */
public class Transaction {
    public String id;
    public String other;
    public String amount;
    public String time;
    public String bank;
    public String balance;

    Transaction() {
        id="";
        other="";
        amount="";
        time="";
        bank="";
        balance="";
    }


   public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
