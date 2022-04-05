package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;

import static io.restassured.RestAssured.given;

public class OrderClient extends StellarBurgersRestClient {
    public final String ORDER_PATH = BASE_URL + "orders/";

    @Step("Успешное создание заказа")
    public Response getSuccessfulCreateOrderResponse (Ingredient ingredients, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(ingredients)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получить заказы конкретного покупателя")
    public Response getOrdersFromSpecificCustomerResponse(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDER_PATH);
    }
}
