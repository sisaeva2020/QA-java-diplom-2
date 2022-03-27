package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Description("Покупатель с авторизацией может успешно изменить email")
    @Test
    public void customerWithAuthCanBeChangeEmail() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(email, customer.password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(changing);
    }
    @Description("Покупатель с авторизацией может успешно изменить пароль")
    @Test
    public void customerWithAuthCanBeChangePassword() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, password, customer.name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(changing);
    }

    @Description("Покупатель с авторизацией может успешно изменить имя")
    @Test
    public void customerWithAuthCanBeChangeName() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        changing = customerClient.successfulChangingCustomerData(new Customer(customer.email, customer.password, name), accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(changing);
    }

    @Description("Изменение email без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangeEmailWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(email, customer.password, customer.name), "");
        assertEquals(401, statusCode);
    }

    @Description("Изменение пароля без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangePasswordWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, password, customer.name), "");
        assertEquals(401, statusCode);
    }

    @Description("Изменение имени без авторизации возвращает statusCode 401")
    @Test
    public void errorWhenChangeNameWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = customerClient.successfulChangingCustomerDataStatusCode(new Customer(customer.email, customer.password, name), "");
        assertEquals(401, statusCode);
    }

}

