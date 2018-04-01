package com.efrei.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class ResponseSigninRecord implements Serializable {
    private String status;
    private ArrayList<SignIn> msg;

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setMsg(ArrayList<SignIn> msg) {
        this.msg = msg;
    }
    public ArrayList<SignIn> getMsg() {
        return msg;
    }
}


