package com.example.dietaryNutrition;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Gender {
        Male, Female, NonBinary
    }

    private String email;
    private String name;
    private String password;
    private float height;
    private float weight;
    private int age;
    private Gender gender;

    public Member() {

    }

    public int CheckFormat1()
    {
        Pattern ptr = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if (email.length() == 0 || name.length() == 0) {
            return -1;
        }
        else if(!ptr.matcher(email).matches()){
            return -2;
        }
        else {
            return 0;
        }
    }

    public int CheckFormat2()
    {
        if (password.length() < 6) {
            return -1;
        }
        else {
            return 0;
        }
    }

    public int CheckFormat3()
    {
        if (age > 200 || height > 300.0f || weight > 500.0f) {
            return -1;
        }
        else {
            return 0;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}