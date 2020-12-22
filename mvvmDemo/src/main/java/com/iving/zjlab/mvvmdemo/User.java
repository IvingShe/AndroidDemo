package com.iving.zjlab.mvvmdemo;

/**
 * author: li.she
 * date: 17:15 2020/11/16
 * description:
 */
public class User {

    private final String firstName;
    private final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
}
