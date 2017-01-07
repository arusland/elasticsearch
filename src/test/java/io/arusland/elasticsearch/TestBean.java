package io.arusland.elasticsearch;

import java.util.Date;

/**
 * Created by ruslan on 07.01.2017.
 */
public class TestBean {
    private String name;
    private int age;
    private Date birth;

    public TestBean(String name, int age, Date birth) {
        this.name = name;
        this.age = age;
        this.birth = birth;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
