package ru.yandex.praktikum.stellarBurgers.api.model;

import org.apache.commons.lang3.RandomStringUtils;

public class Customer {
    public String email;
    public String password;
    public String name;

    public Customer (String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Customer getRandom() {
        String email = RandomStringUtils.randomAlphabetic(6) + "@test.ru";
        String password = RandomStringUtils.randomNumeric(6);
        String name = RandomStringUtils.randomAlphabetic(6);
        return new Customer (email, password, name);
    }

    @Override
    public String toString() {
        return "Customer {email:"+email+",password:"+password+",name:"+name+"}";
    }
}
