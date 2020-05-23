package com.google.codelabs.mdc.java.smartburger.models;

public class User {
    public String env;
    public String name;
    public String lastname;
    public int dni;
    public String email;
    public String password;
    public int commission;
    public int group;

    public User(){
        this.env = "";
        this.name = "";
        this.lastname = "";
        this.dni = 1;
        this.email = "";
        this.password = "";
        this.commission = 1;
        this.group = 1;
    }


    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
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

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
