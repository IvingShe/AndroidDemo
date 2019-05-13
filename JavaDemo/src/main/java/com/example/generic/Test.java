package com.example.generic;

import java.util.ArrayList;
import java.util.List;


public class Test {


    /***
     * 对于集合没有泛型的情况：
     * 1.放入集合中可以是任何类型的对象‘
     * 2.获取对象需要强制类型转换；
     */
    public void test(){

        List persons = new ArrayList();
        //1.放入集合中可以是任何类型的对象:person对象；
        persons.add(new Person("AAA",12));
        persons.add(new Person("BB",32));
        persons.add(new Person("cc",72));
       //1.放入集合中可以是任何类型的对象:String；
        persons.add("String");

        for(int i=0;i<persons.size();i++){
            //2.获取对象需要强制类型转换；
            Person p= (Person) persons.get(i);
        }
    }

    /***
     * 对于集合有泛型的情况：
     * 1.放入集合中只可以是特定类型的对象‘
     * 2.获取对象不需要强制类型转换；
     */
    public void testGeneric(){
        List<Person> persons= new ArrayList<>();
        persons.add(new Person("AAA",12));
        persons.add(new Person("BB",32));
        persons.add(new Person("cc",72));
        //1.放入String类型的对象,会报错；与test方法比较；
       // persons.add("String");

        for(int i=0;i<persons.size();i++){
            Person p= persons.get(i); //2.获取对象不需要强制类型转换；
            System.out.println(p);
        }

        Person[] personArray =persons.toArray( new Person[1]);
        System.out.println("personArray length ="+personArray.length);
    }

    /**
     * 一个演示：简单的泛型定义和使用；
     * */

    public void testASimpleGenericClass(){
        Dao<Person> dao= new Dao<>();
        dao.save(new Person("AAA",12));
        dao.save(new Person("BB",32));
        dao.save(new Person("cc",72));

        Person data = dao.get(1);

        System.out.println(data);
    }



    /**
     * 一个演示：测试泛型的通配符
     */
    public void testGenericWildcard(){

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("AAA",12));
        persons.add(new Person("BB",32));
        persons.add(new Person("cc",72));
        println(persons);

    }
    private  void println(List<Person> persons){
        for(Person p:persons){
            System.out.println(p);
        }
    }


    public void testGenericWildcard2(){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("AAA",14));
        persons.add(new Person("BB",35));
        persons.add(new Person("cc",62));
        println2(persons);


        List<Student>  students= new ArrayList<>();
        students.add(new Student("AAA",12,"uestc"));
        students.add(new Student("BB",42,"uestc"));
        students.add(new Student("cc",72,"uestc"));
        println2(students);


    }

    /**
     * 泛型的通配符:Person 及其子类；
     * @param persons
     */
    private  void println2(List<? extends Person> persons){
        for(Person p:persons){
            System.out.println(p);
        }
    }
}

