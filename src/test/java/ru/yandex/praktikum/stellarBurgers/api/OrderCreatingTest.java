package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;

import static org.junit.Assert.*;


public class OrderCreatingTest {
    private CustomerClient customerClient;
    private OrderClient orderClient;
    private Ingredient ingredients;
    private Customer customer;
    String accessToken;
    boolean newOrder;
    int statusCode;
    String orderError;
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
    String[] incorrectHashIngr = new String[]{"61c0c5a71d1f82001bdaaa", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @DisplayName("Успешное создание заказа возвращает True")
    @Test
    public void orderWithAuthReturnTrue() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrderReturnTrue(new Ingredient(orderComposition), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertTrue(newOrder);
    }

    @DisplayName("Успешное создание заказа возвращает StatusCode 200")
    @Test
    public void orderWithAuthReturnStatusCode200() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderReturnStatusCode(new Ingredient(orderComposition), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, statusCode);
    }

    @DisplayName("Создание заказа с нулевым токеном возвращает False")
    @Test
    public void errorWhenCreateOrderWithoutAuthReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrderReturnTrue(new Ingredient(orderComposition), "");
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertFalse(newOrder);
    }

    @DisplayName("Создание заказа с нулевым токеном возвращает statusCode 401")
    @Test
    public void errorWhenCreateOrderWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderReturnStatusCode(new Ingredient(orderComposition), "");
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! Создался заказ без авторизации!", 401, statusCode);
    }

    @DisplayName("Создание заказа без токена возвращает exception")
    @Test
    public void errorWhenCreateOrderWithoutAuthReturnException() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.createOrderReturnStatusCode(new Ingredient(orderComposition), null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Создание заказа без ингредиентов возвращает False")
    @Test
    public void errorWhenCreateOrderWithoutIngredientsReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrderReturnTrue(new Ingredient(null), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertFalse(newOrder);
    }

    @DisplayName("Создание заказа без ингредиентов возвращает statusCode 400")
    @Test
    public void errorWhenCreateOrderWithoutIngredientsReturnStatusCode400() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderReturnStatusCode(new Ingredient(null), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! Создался заказ без ингредиентов", 400, statusCode);
    }

    @DisplayName("Создание заказа с некорректным хэшем ингредиентов возвращает Internal Server Error")
    @Test
    public void errorWhenCreateOrderWithIncorrectIngredientsHashReturnFalse() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderError = orderClient.createOrderReturnError(new Ingredient(incorrectHashIngr), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Internal Server Error", orderError.substring(107, 128));
    }

    @DisplayName("Создание заказа с некорректным хэшем ингредиентов возвращает statusCode 500")
    @Test
    public void errorWhenCreateOrderWithIncorrectIngredientsHashReturnStatusCode500() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderReturnStatusCode(new Ingredient(incorrectHashIngr), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! Создался заказ с некорректными ингредиентами", 500, statusCode);
    }
}