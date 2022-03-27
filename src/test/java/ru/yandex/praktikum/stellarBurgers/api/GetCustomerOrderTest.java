package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Description;
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
    boolean newOrder;
    int statusCode;
    boolean getOrder;
    String[] orderComposition = new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};


    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        orderClient = new OrderClient();
    }

    @Description("Успешный запрос заказов конкретного покупателя с авторизацией")
    @Test
    public void getCustomerOrderWithAuth() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrder(new Ingredient(orderComposition), accessToken);
        getOrder = orderClient.getOrdersFromSpecificCustomer(accessToken);
        customerClient.deleteCustomer(customer, accessToken);
        assertTrue(getOrder);
    }

    @Description("Запрос заказов конкретного покупателя без авторизации - с нулевым токеном возвращает statusCode 401")
    @Test
    public void getCustomerOrderWithoutAuthReturnStatusCode401() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrder(new Ingredient(orderComposition), accessToken);
        statusCode = orderClient.getOrdersFromSpecificCustomerStatusCode("");
        assertEquals(401, statusCode);

    }

    @Description("Запрос заказов конкретного покупателя без авторизации - без токена возвращает exception")
    @Test
    public void getCustomerOrderWithoutAuthReturnException() {
        Customer customer = Customer.getRandom();
        accessToken = customerClient.createCustomerReturnAccessToken(customer).substring(7);
        newOrder = orderClient.createOrder(new Ingredient(orderComposition), accessToken);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.getOrdersFromSpecificCustomerStatusCode(null);
        });
        String expectedMessage = "accessToken cannot be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }
}
