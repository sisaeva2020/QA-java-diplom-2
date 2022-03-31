package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.CustomerCredentials;

import static org.junit.Assert.*;


public class CustomerCreatingTest {
    private CustomerClient customerClient;
    String accessToken;
    String login;
    boolean success;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    String name = RandomStringUtils.randomAlphabetic(6);
    int statusCode;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @DisplayName("Успешное создание покупателя с корректными данными возвращает не пустой токен")
    @Test
    public void customerCanBeCreatedReturnTokenId() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        customerClient.deleteCustomer(customer, accessToken);
        assertNotNull("Внимание! Вернулся пустой токен", accessToken);
    }

    @DisplayName("Успешное создание покупателя с корректными данными возвращает True")
    @Test
    public void customerCanBeCreatedReturnTrue() {
        Customer customer = Customer.getRandom();
        success = customerClient.createCustomerReturnTrue(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
        customerClient.deleteCustomer(customer, login);
        assertTrue("Внимание! Покупатель не был создан", success);
    }

    @DisplayName("Успешное создание покупателя с корректными данными возвращает StatusCode 200")
    @Test
    public void customerCanBeCreatedReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
        customerClient.deleteCustomer(customer, login);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Попытка регистрации пользователя с данными, которые уже используются, возвращает False")
    @Test
    public void errorWhenCreateCustomerDuplicateReturnFalse() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        success = customerClient.createCustomerReturnTrue(customer);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertFalse("Внимание! Зарегистрирован дубликат покупателя", success);
    }

    @DisplayName("Попытка регистрации пользователя с данными, которые уже используются, возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerDuplicateReturnStatusCode403() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! Зарегистрирован дубликат покупателя", 403, statusCode);
    }

    @DisplayName("Попытка регистрации пользователя без email возвращает False")
    @Test
    public void errorWhenCreateCustomerWithoutEmailReturnFalse() {
        Customer customer = new Customer(null, password, name);
        success = customerClient.createCustomerReturnTrue(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (NullPointerException exception) {
        } catch (IllegalArgumentException exception) {
        }
        assertFalse("Внимание! Зарегистрирован покупатель без email", success);
    }

    @DisplayName("Попытка регистрации пользователя без email возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutEmailReturnStatusCode403() {
        Customer customer = new Customer(null, password, name);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! Зарегистрирован покупатель без email", 403, statusCode);
    }

    @DisplayName("Попытка регистрации пользователя без пароля возвращает False")
    @Test
    public void errorWhenCreateCustomerWithoutPasswordReturnFalse() {
        Customer customer = new Customer(email, null, name);
        success = customerClient.createCustomerReturnTrue(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertFalse("Внимание! Зарегистрирован покупатель без пароля", success);
    }

    @DisplayName("Попытка регистрации пользователя без пароля возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutPasswordReturnStatusCode403() {
        Customer customer = new Customer(email, null, name);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! Зарегистрирован покупатель без пароля", 403, statusCode);
    }

    @DisplayName("Попытка регистрации пользователя без имени возвращает False")
    @Test
    public void errorWhenCreateCustomerWithoutNameReturnFalse() {
        Customer customer = new Customer(email, password, null);
        success = customerClient.createCustomerReturnTrue(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertFalse("Внимание! Зарегистрирован покупатель без имени", success);
    }

    @DisplayName("Попытка регистрации пользователя без имени возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutNameReturnStatusCode403() {
        Customer customer = new Customer(email, password, null);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        try {
            login = customerClient.authCustomerWithValidDataReturnAccessToken(customerCredentials).substring(7);
            customerClient.deleteCustomer(customer, login);
        } catch (IllegalArgumentException exception) {
        } catch (NullPointerException exception) {
        }
        assertEquals("Внимание! Зарегистрирован покупатель без имени", 403, statusCode);
    }
}