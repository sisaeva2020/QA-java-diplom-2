package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Step;
import ru.yandex.praktikum.stellarBurgers.api.model.Ingredient;

import static io.restassured.RestAssured.given;

public class OrderClient extends StellarBurgersRestClient {
    public final String ORDER_PATH = BASE_URL + "orders/";

    @Step("Успешное создание заказа  возвращает True")
    public boolean createOrderReturnTrue(Ingredient ingredients, String accessToken) {
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
    public int createOrderReturnStatusCode(Ingredient ingredients, String accessToken) {
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

    @Step("Cоздание заказа с неверным хэшем ингредиента возвращает Internal Server Error")
    public String createOrderReturnError(Ingredient ingredients, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract()
                .body()
                .asPrettyString();
    }

    @Step("Получить заказы конкретного покупателя возвращает True")
    public boolean getOrdersFromSpecificCustomerReturnTrue(String accessToken) {
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
    public int getOrdersFromSpecificCustomerReturnStatusCode(String accessToken) {
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
