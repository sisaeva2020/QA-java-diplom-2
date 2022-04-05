package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
    String[] incorrectHashIngr = new String[]{"61c0c5a71d1f82001bdaaa", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @DisplayName("Успешное создание заказа")
    @Test
    public void orderWithAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! Заказ не создан", response.then().extract().path("success"));
    }

    @DisplayName("Создание заказа с нулевым токеном")
    @Test
    public void errorWhenCreateOrderWithoutToken() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), "");
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Создался заказ без авторизации!", response.then().extract().path("success"));
    }

    @DisplayName("Создание заказа без токена")
    @Test
    public void errorWhenCreateOrderWithNullToken() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Response response = orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertTrue("Внимание! Создался заказ без токена!", actualMessage.contains(expectedMessage));
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void errorWhenCreateOrderWithoutIngredients() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = orderClient.getSuccessfulCreateOrderResponse(new Ingredient(null), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 400)", 400, response.statusCode());
        assertFalse("Внимание! Создался заказ без ингредиентов!", response.then().extract().path("success"));
    }

    @DisplayName("Создание заказа с некорректным хэшем ингредиентов")
    @Test
    public void errorWhenCreateOrderWithIncorrectIngredientsHash() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        Response response = orderClient.getSuccessfulCreateOrderResponse(new Ingredient(incorrectHashIngr), accessToken);
        customerClient.deleteCustomer(new Customer(customer.email, customer.password, customer.name), accessToken);
        assertEquals("Internal Server Error", response.then().extract().body().asPrettyString().substring(107, 128));
        assertEquals("Внимание! StatusCode некорректный (не 500)", 500, response.statusCode());
    }
}