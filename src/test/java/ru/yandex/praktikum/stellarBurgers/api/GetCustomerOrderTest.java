package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;

import static org.junit.Assert.*;


public class GetCustomerOrderTest {
    private CustomerClient customerClient;
    private OrderClient orderClient;
    private Ingredient ingredients;
    String accessToken;
    boolean success;
    int statusCode;
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @DisplayName("Успешный запрос заказов конкретного покупателя с авторизацией возвращает True")
    @Test
    public void getCustomerOrderWithAuthReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        success = orderClient.getOrdersFromSpecificCustomerReturnTrue(accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(success);
    }

    @DisplayName("Успешный запрос заказов конкретного покупателя с авторизацией возвращает StatusCode 200")
    @Test
    public void getCustomerOrderWithAuthReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        statusCode = orderClient.getOrdersFromSpecificCustomerReturnStatusCode(accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Запрос заказов конкретного покупателя без авторизации - с нулевым токеном возвращает False")
    @Test
    public void getCustomerOrderWithoutAuthReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        success = orderClient.getOrdersFromSpecificCustomerReturnTrue("");
        customerClient.deleteCustomer(customer, accessToken);
        assertFalse(success);
    }

    @DisplayName("Запрос заказов конкретного покупателя без авторизации - с нулевым токеном возвращает statusCode 401")
    @Test
    public void getCustomerOrderWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        statusCode = orderClient.getOrdersFromSpecificCustomerReturnStatusCode("");
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! Запрошен список заказов покупателя с нулевым токеном", 401, statusCode);
    }

    @DisplayName("Запрос заказов конкретного покупателя без авторизации - без токена возвращает exception")
    @Test
    public void getCustomerOrderWithoutAuthReturnException() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.getOrdersFromSpecificCustomerReturnStatusCode(null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
