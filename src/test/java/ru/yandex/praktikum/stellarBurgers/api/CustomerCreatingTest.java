package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import static org.junit.Assert.*;


public class CustomerCreatingTest {
    private CustomerClient customerClient;
    String accessToken;
    String email = RandomStringUtils.randomAlphabetic(6) + "test.ru";
    String password = RandomStringUtils.randomNumeric(6);
    String name = RandomStringUtils.randomAlphabetic(6);
    int statusCode;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
    }

    @Description("Успешное создание покупателя с корректными данными возвращает не пустой токен")
    @Test
    public void customerCanBeCreatedReturnTokenId() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        customerClient.deleteCustomer(customer, accessToken);
        assertNotNull(accessToken);
    }

    @Description("Попытка регистрации пользователя с данными, которые уже используются, возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerDuplicate() {
        Customer customer = Customer.getRandom();
        customerClient.createCustomerReturnStatusCode(customer);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        assertEquals(403, statusCode);
    }

    @Description("Попытка регистрации пользователя без email возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutEmail() {
        Customer customer = new Customer(null, password, name);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        assertEquals(403, statusCode);
    }

    @Description("Попытка регистрации пользователя без пароля возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutPassword() {
        Customer customer = new Customer(email, null, name);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        assertEquals(403, statusCode);
    }

    @Description("Попытка регистрации пользователя без имени возвращает statusCode 403")
    @Test
    public void errorWhenCreateCustomerWithoutName() {
        Customer customer = new Customer(email, password, null);
        statusCode = customerClient.createCustomerReturnStatusCode(customer);
        assertEquals(403, statusCode);
    }
}