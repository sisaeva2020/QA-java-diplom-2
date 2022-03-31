package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
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

    @DisplayName("Успешная авторизация возвращает True")
    @Test
    public void customerCanBeLoginWithValidDataReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        success = customerClient.authCustomerWithValidDataReturnTrue(customerCredentials);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(success);
    }

    @DisplayName("Успешная авторизация возвращает StatusCode 200")
    @Test
    public void customerCanBeLoginWithValidDataReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("При авторизации с некорректным email возвращается False")
    @Test
    public void errorWhenLoginWithIncorrectEmailReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, customer.password);
        success = customerClient.authCustomerWithValidDataReturnTrue(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse(success);
    }

    @DisplayName("При авторизации с некорректным email возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectEmailReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, customer.password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Авторизирован покупатель с некорректным email", 401, statusCode);
    }

    @DisplayName("При авторизации с некорректным паролем возвращается False")
    @Test
    public void errorWhenLoginWithIncorrectPasswordReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, password);
        success = customerClient.authCustomerWithValidDataReturnTrue(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse(success);
    }

    @DisplayName("При авторизации с некорректным паролем возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectPasswordReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Авторизирован покупатель с некорректным паролем", 401, statusCode);
    }

    @DisplayName("При авторизации с некорректным email и паролем возвращается False")
    @Test
    public void errorWhenLoginWithIncorrectEmailAndPasswordReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, password);
        success = customerClient.authCustomerWithValidDataReturnTrue(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse(success);
    }

    @DisplayName("При авторизации с некорректным email и паролем возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectEmailAndPasswordReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Авторизирован покупатель с некорректным email и паролем", 401, statusCode);
    }
}