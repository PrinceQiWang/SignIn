package com.efrei.bean;

import java.io.Serializable;

public class ResponseHoliday implements Serializable {
    private String status;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}


