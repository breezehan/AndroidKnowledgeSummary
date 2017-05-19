package com.breezehan.ipc;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -765267859799146771L;
    private int userId;
    private String userName;
    private boolean isMale;
    private String biaozhi;

    public User() {
    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
