package com.example.generic;

public class Student extends Person {

    private String school;

    public Student(String name, int age, String school) {
        super(name, age);
        this.school = school;
    }


    @Override
    public String toString() {
        return "Student{" +
                "school='" + school + '\'' +
                ", name ='" + name + '\'' +
                ", age =" + age +
                '}';
    }
}
