package ru.yandex.praktikum.stellarBurgers.api.model;

public class CustomerCredentials {
    public String email;
    public String password;

    public CustomerCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerCredentials {email:" + email + ",password:" + password + "}";
    }
}
