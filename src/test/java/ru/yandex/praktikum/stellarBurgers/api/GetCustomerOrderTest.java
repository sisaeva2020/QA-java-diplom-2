package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @DisplayName("Успешный запрос заказов конкретного покупателя с авторизацией")
    @Test
    public void getCustomerOrderWithAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), accessToken);
        Response response = orderClient.getOrdersFromSpecificCustomerResponse(accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 200)", 200, response.statusCode());
        assertTrue("Внимание! Запрос заказов не удался", response.then().extract().path("success"));
    }

    @DisplayName("Запрос заказов конкретного покупателя без авторизации - без токена")
    @Test
    public void getCustomerOrderWithoutAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), accessToken);
        Response response = orderClient.getOrdersFromSpecificCustomerResponse("");
        customerClient.deleteCustomer(customer, accessToken);
        assertEquals("Внимание! StatusCode некорректный (не 401)", 401, response.statusCode());
        assertFalse("Внимание! Вернулся список заказов покупателя с нулевым токеном", response.then().extract().path("success"));
    }

    @DisplayName("Запрос заказов конкретного покупателя без авторизации - с нулевым токеном")
    @Test
    public void getCustomerOrderWithNullToken() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        orderClient.getSuccessfulCreateOrderResponse(new Ingredient(orderComposition), accessToken);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Response response = orderClient.getOrdersFromSpecificCustomerResponse(null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue("Внимание! Вернулся список заказов покупателя без токена", actualMessage.contains(expectedMessage));
    }
}
