package com.breezehan.rxjava;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

/**
 * Author  hands
 * Description
 * Date    2017/7/28 14:41
 * Version
 */

public class User {
    private String name;
    private int age;
    public User() {
//        Logger.d("initialize +1");
    }

    public User(String name, int age) {
//        Logger.d("initialize +1");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
