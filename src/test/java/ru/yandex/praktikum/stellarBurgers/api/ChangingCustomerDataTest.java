package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;

import static org.junit.Assert.*;

public class ChangingCustomerDataTest {
    private CustomerClient customerClient;
    String accessToken;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    String name = RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @DisplayName("Успешное изменение email покупателя с авторизацией")
    @Test
    public void customerWithAuthCanBeChangeEmail() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(email, customer.password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! email не был изменен", response.then().extract().path("success"));
    }

    @DisplayName("Успешное изменение пароля покупателя с авторизацией")
    @Test
    public void customerWithAuthCanBeChangePassword() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(customer.email, password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! пароль не был изменен", response.then().extract().path("success"));
    }

    @DisplayName("Успешное изменение имени покупателя с авторизацией")
    @Test
    public void customerWithAuthCanBeChangeName() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(customer.email, customer.password, name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! имя не было изменено", response.then().extract().path("success"));
    }

    @DisplayName("Изменение email без авторизации")
    @Test
    public void errorWhenChangeEmailWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(email, customer.password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Изменен email без авторизации", response.then().extract().path("success"));
    }

    @DisplayName("Изменение пароля без авторизации")
    @Test
    public void errorWhenChangePasswordWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(customer.email, password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Изменен пароль без авторизации", response.then().extract().path("success"));
    }

    @DisplayName("Изменение имени без авторизации")
    @Test
    public void errorWhenChangeNameWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = customerClient.getSuccessfulChangingCustomerDataResponse(new Customer(customer.email, customer.password, name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Изменено имя без авторизации", response.then().extract().path("success"));
    }
}

