package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;

import static org.junit.Assert.*;

public class ChangingCustomerDataTest {
    private CustomerClient customerClient;
    String accessToken;
    boolean changing;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    String name = RandomStringUtils.randomAlphabetic(6);
    int statusCode;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @DisplayName("Успешное изменение email покупателя с авторизацией возвращает True")
    @Test
    public void customerWithAuthCanBeChangeEmailReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(email, customer.password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue("Внимание! email не был изменен", changing);
    }

    @DisplayName("Успешное изменение email покупателя с авторизацией возвращает StatusCode200")
    @Test
    public void customerWithAuthCanBeChangeEmailReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(email, customer.password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Успешное изменение пароля покупателя с авторизацией возвращает True")
    @Test
    public void customerWithAuthCanBeChangePasswordReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue("Внимание! пароль не был изменен", changing);
    }

    @DisplayName("Успешное изменение пароля покупателя с авторизацией возвращает statusCode 200")
    @Test
    public void customerWithAuthCanBeChangePasswordReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Успешное изменение имени покупателя с авторизацией возвращает True")
    @Test
    public void customerWithAuthCanBeChangeNameReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, customer.password, name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue("Внимание! имя не было изменено", changing);
    }

    @DisplayName("Успешное изменение имени покупателя с авторизацией возвращает statusCode 200")
    @Test
    public void customerWithAuthCanBeChangeNameReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, customer.password, name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Изменение email без авторизации возвращает False")
    @Test
    public void errorWhenChangeEmailWithoutAuthReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(email, customer.password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse("Внимание! Изменен email без авторизации", changing);
    }

    @DisplayName("Изменение email без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangeEmailWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(email, customer.password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Изменили email покупателю без авторизации", 401, statusCode);
    }

    @DisplayName("Изменение пароля без авторизации возвращает False")
    @Test
    public void errorWhenChangePasswordWithoutAuthReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse("Внимание! Изменен пароль без авторизации", changing);
    }

    @DisplayName("Изменение пароля без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangePasswordWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, password, customer.name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Изменили пароль покупателю без авторизации", 401, statusCode);
    }

    @DisplayName("Изменение имени без авторизации возвращает False")
    @Test
    public void errorWhenChangeNameWithoutAuthReturnFalseReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, customer.password, name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertFalse("Внимание! Изменено имя без авторизации", changing);
    }

    @DisplayName("Изменение имени без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangeNameWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, customer.password, name), "");
        try {
            customerClient.deleteCustomer(customer, accessToken);
        } catch (IllegalArgumentException exception) {
        }
        assertEquals("Внимание! Изменили имя покупателю без авторизации", 401, statusCode);
    }
}

