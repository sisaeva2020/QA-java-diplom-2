package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Description;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;

import static org.junit.Assert.*;


public class OrderCreatingTest {
    private CustomerClient customerClient;
    private OrderClient orderClient;
    private Ingredient ingredients;
    String accessToken;
    boolean newOrder;
    int statusCode;
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
    String[] incorrectHashIngr = new String[]{"61c0c5a71d1f82001bdaaa", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};


    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @Description("Заказ можно успешно создать создать с авторизацией")
    @Test
    public void orderWithAuthCanBeCreatedSuccessfully() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrder(new Ingredient(orderComposition), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertTrue(newOrder);
    }

    @Description("Создание заказа без авторизации - с нулевым токеном возвращает statusCode 401")
    @Test
    public void errorWhenCreateOrderWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderStatusCode(new Ingredient(orderComposition), "");
        assertEquals("Внимание! Создался заказ без авторизации!", 401, statusCode);
    }

    @Description("Создание заказа без авторизации - без токена возвращает exception")
    @Test
    public void errorWhenCreateOrderWithoutAuthReturnException() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.createOrderStatusCode(new Ingredient(orderComposition), null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Description("Создание заказа без ингредиентов возвращает statusCode 400")
    @Test
    public void errorWhenCreateOrderWithoutIngredients() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderStatusCode(new Ingredient(null), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals(400, statusCode);
    }

    @Description("Создание заказа с некорректным хэшем ингредиентов возвращает statusCode 500")
    @Test
    public void errorWhenCreateOrderWithIncorrectIngredientsHash() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        statusCode = orderClient.createOrderStatusCode(new Ingredient(incorrectHashIngr), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals(500, statusCode);
    }
}