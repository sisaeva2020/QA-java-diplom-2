package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;

import static org.junit.Assert.*;


public class CustomerCreatingTest {
    private CustomerClient customerClient;
    String accessToken;
    String login;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    String name = RandomStringUtils.randomAlphabetic(6);

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @DisplayName("Успешное создание покупателя с корректными данными")
    @Test
    public void customerCanBeCreatedSuccessfully() {
        Customer customer = Customer.getRandom();
        Response response = customerClient.getSuccessfulCreateCustomerResponse(customer);
        accessToken = response.then().extract().path("accessToken");
        customerClient.deleteCustomer(customer, accessToken.substring(7));
        assertNotNull("Внимание! Вернулся пустой токен", accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! Покупатель не был создан", response.then().extract().path("success"));
    }

    @DisplayName("Регистрация дубликата пользователя")
    @Test
    public void errorWhenCreateCustomerDuplicate() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        Response response = customerClient.getSuccessfulCreateCustomerResponse(customer);
        try {
            login = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 403)", 403, response.statusCode());
        assertFalse("Внимание! Зарегистрирован дубликат покупателя", response.then().extract().path("success"));
    }

    @DisplayName("Попытка регистрации пользователя без email")
    @Test
    public void errorWhenCreateCustomerWithoutEmail() {
        Customer customer = new Customer(null, password, name);
        Response response = customerClient.getSuccessfulCreateCustomerResponse(customer);
        try {
            login = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, login);
        } catch (NullPointerException exception) {
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 403)", 403, response.statusCode());
        assertFalse("Внимание! Зарегистрирован покупатель без email", response.then().extract().path("success"));
    }

    @DisplayName("Попытка регистрации пользователя без пароля")
    @Test
    public void errorWhenCreateCustomerWithoutPassword() {
        Customer customer = new Customer(email, null, name);
        Response response = customerClient.getSuccessfulCreateCustomerResponse(customer);
        try {
            login = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, login);
        } catch (NullPointerException exception) {
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 403)", 403, response.statusCode());
        assertFalse("Внимание! Зарегистрирован покупатель без пароля", response.then().extract().path("success"));
    }

    @DisplayName("Попытка регистрации пользователя без имени")
    @Test
    public void errorWhenCreateCustomerWithoutName() {
        Customer customer = new Customer(email, password, null);
        Response response = customerClient.getSuccessfulCreateCustomerResponse(customer);
        try {
            login = response.then().extract().path("accessToken");
            customerClient.deleteCustomer(customer, login);
        } catch (NullPointerException exception) {
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! StatusCode некорректный (не 403)", 403, response.statusCode());
        assertFalse("Внимание! Зарегистрирован покупатель без имени", response.then().extract().path("success"));
    }
}