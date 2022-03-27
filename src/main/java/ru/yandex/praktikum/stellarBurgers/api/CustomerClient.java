package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Step;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.CustomerCredentials;
import static io.restassured.RestAssured.given;

public class CustomerClient extends StellarBurgersRestClient {
    public final String PATH = BASE_URL + "auth/";
    Customer customer;

    @Step("Создание покупателя {customer} возвращает Token")
    public String createCustomerReturnAccessToken(Customer customer) {
        return given()
                .spec(getBaseSpec())
                .body(customer)
                .when()
                .post(PATH + "register/")
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Создание покупателя {customer} возвращает statusCode")
    public int createCustomerReturnStatusCode(Customer customer) {
        return given()
                .spec(getBaseSpec())
                .body(customer)
                .when()
                .post(PATH + "register/")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Изменение данных покупателя {customer}")
    public boolean successfulChangingCustomerData(Customer customer, String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(customer)
                .when()
                .patch(PATH + "user/")
                .then()
                .extract()
                .path("success");
}
    @Step("Изменение данных покупателя  {customer} возвращает statusCode")
    public int successfulChangingCustomerDataStatusCode(Customer customer, String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(customer)
                .when()
                .patch(PATH + "user/")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Успешная авторизация {customerCredentials}")
    public boolean authCustomerWithValidData (CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .path("success");
    }

    @Step("Успешная авторизация {customerCredentials} возвращает statusCode 200")
    public int authCustomerWithValidDataReturnStatusCode (CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Удаление покупателя {customer}")
    public boolean deleteCustomer(Customer customer, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(customer)
                .when()
                .delete(PATH + "user/")
                .then()
                .extract()
                .path("success");
    }
}
