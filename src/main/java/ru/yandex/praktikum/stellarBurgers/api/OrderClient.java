package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Step;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;
import static io.restassured.RestAssured.given;

public class OrderClient extends StellarBurgersRestClient {
    public final String ORDER_PATH = BASE_URL + "orders/";

    @Step("Успешное создание заказа")
    public boolean createOrder(Ingredient ingredients, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract()
                .path("success");
    }
    @Step("Успешное создание заказа возвращает statusCode 200")
    public int createOrderStatusCode(Ingredient ingredients, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract()
                .statusCode();
    }

    @Step("Получить заказы конкретного покупателя")
    public boolean getOrdersFromSpecificCustomer(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDER_PATH)
                .then()
                .extract()
                .path("success");
    }
    @Step("Получить заказы конкретного покупателя statusCode")
    public int getOrdersFromSpecificCustomerStatusCode(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDER_PATH)
                .then()
                .extract()
                .statusCode();
    }

}
