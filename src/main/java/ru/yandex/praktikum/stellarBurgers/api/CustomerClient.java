package ru.yandex.praktikum.stellarBurgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.stellarBurgers.api.model.Customer;
import ru.yandex.praktikum.stellarBurgers.api.model.CustomerCredentials;

import static io.restassured.RestAssured.given;

public class CustomerClient extends StellarBurgersRestClient {
    public final String PATH = BASE_URL + "auth/";
    Customer customer;

    @Step("Создание покупателя {customer}")
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

    @Step("Создание покупателя {customer}")
    public Response getSuccessfulCreateCustomerResponse(Customer customer) {
        return given()
                .spec(getBaseSpec())
                .body(customer)
                .when()
                .post(PATH + "register/");
    }

    @Step("Изменение данных покупателя {customer}")
    public Response getSuccessfulChangingCustomerDataResponse(Customer customer, String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(customer)
                .when()
                .patch(PATH + "user/");
    }

    @Step("Успешная авторизация {customerCredentials}")
    public Response getAuthCustomerWithValidDataResponse(CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .post(PATH + "login/");
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
