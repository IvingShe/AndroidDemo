package com.iving.greendaodemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Person {
    //@id：通过这个注解标记的字段必须是Long类型的，这个字段在数据库中表示它就是主键，并且它默认就是自增的
    //@id
    @Id(autoincrement = true)
    public Long id;
    public String UserName;
    public String PassWord;

    //@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
    @Transient
    public String Mobile; // not persisted
    @Generated(hash = 1684084126)
    public Person(Long id, String UserName, String PassWord) {
        this.id = id;
        this.UserName = UserName;
        this.PassWord = PassWord;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long Id) {
        this.id = Id;
    }
    public String getUserName() {
        return this.UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public String getPassWord() {
        return this.PassWord;
    }
    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }
    public void setId(Long id) {
        this.id = id;
    }


}