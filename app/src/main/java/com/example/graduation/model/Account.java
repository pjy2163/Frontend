package com.example.graduation.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("bank_name")
    private String bank_name;
    @SerializedName("account_number")
    private String account_number;
    @SerializedName("account_pwd")
    private String account_pwd;

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }


    public void getBank_name(String bank_name) {
        this.bank_name= bank_name;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }


    public void getAccount_number(String account_number) {
        this.account_number= account_number;
    }

    public void setAccount_pwd(String account_pwd) {
        this.account_pwd = account_pwd;
    }


    @Override
    public String toString() {
        return "Account{" +
                "bank_name=" + bank_name +
                ", account_number='" + account_number + '\'' +
                ", account_pwd='" + account_pwd + '\'' +
                '}';
    }
}