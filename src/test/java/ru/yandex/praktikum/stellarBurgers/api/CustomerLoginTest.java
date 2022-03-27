package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.CustomerCredentials;
import static org.junit.Assert.*;


public class CustomerLoginTest {
    private CustomerClient customerClient;
    String accessToken;
    boolean login;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    int statusCode;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @Description("Успешно созданный покупатель может залогиниться")
    @Test
    public void customerCanBeLoginWithValidData() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, customer.password);
        login = customerClient.authCustomerWithValidData(customerCredentials);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(login);
    }
    @Description("При авторизации с некорректным email возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectEmail() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, customer.password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        assertEquals(401, statusCode);
    }
    @Description("При авторизации с некорректным паролем возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectPassword() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(customer.email, password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        assertEquals(401, statusCode);
    }
    @Description("При авторизации с некорректным email и паролем возвращается statusCode 401")
    @Test
    public void errorWhenLoginWithIncorrectEmailAndPassword() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnAccessToken(customer);
        CustomerCredentials customerCredentials = new CustomerCredentials(email, password);
        statusCode = customerClient.authCustomerWithValidDataReturnStatusCode(customerCredentials);
        assertEquals(401, statusCode);
    }




}