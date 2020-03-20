package com.iving.greendaodemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class Person {
    //@Id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    //@Id
    @org.greenrobot.greendao.annotation.Id
    public int Id;
    private String UserName;
    private String PassWord;
    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
    @Transient
    private String Mobile; // not persisted

    public Person(int id) {
        Id = id;
    }
}