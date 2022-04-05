package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.CustomerCredentials;

import static org.junit.Assert.*;


public class CustomerLoginTest {
    private CustomerClient customerClient;
    String accessToken;
    boolean success;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    int statusCode;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @DisplayName("Успешная авторизация")
    @Test
    public void customerCanBeLoginWithValidData() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        Response response = customerClient.getAuthCustomerWithValidDataResponse(customerCredentials);
        accessToken = response.then().extract().path("accessToken");
        customerClient.deleteCustomer(customer, accessToken.substring(7));
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! Авторизация не удалась", response.then().extract().path("success"));
    }

    @DisplayName("Авторизация с некорректным email")
    @Test
    public void errorWhenLoginWithIncorrectEmail() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, customer.password);
        Response response = customerClient.getAuthCustomerWithValidDataResponse(customerCredentials);
        try {
            accessToken = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, accessToken.substring(7));
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Авторизирован покупатель с некорректным email", response.then().extract().path("success"));
    }

    @DisplayName("Авторизация с некорректным паролем")
    @Test
    public void errorWhenLoginWithIncorrectPassword() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, password);
        Response response = customerClient.getAuthCustomerWithValidDataResponse(customerCredentials);
        try {
            accessToken = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, accessToken.substring(7));
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Авторизирован покупатель с некорректным паролем", response.then().extract().path("success"));
    }

    @DisplayName("Авторизация с некорректным email и паролем")
    @Test
    public void errorWhenLoginWithIncorrectEmailAndPassword() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, password);
        Response response = customerClient.getAuthCustomerWithValidDataResponse(customerCredentials);
        try {
            accessToken = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, accessToken.substring(7));
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Авторизирован покупатель с некорректным email и паролем", response.then().extract().path("success"));
    }
}