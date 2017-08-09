package com.breezehan.serializable;

import java.io.Serializable;

/**
 * Author  hands
 * Description
 * Date    2017/8/9 10:48
 * Version
 */

public class User implements Serializable{
    private static final long serialVersionUID = -9068471045426611656L;
    private String age;
    private String name;
    private String isMale;
    public User(String age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
