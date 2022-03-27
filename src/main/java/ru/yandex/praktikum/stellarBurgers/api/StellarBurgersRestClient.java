package ru.yandex.praktikum.stellarBurgers.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class StellarBurgersRestClient {

    public final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";

    protected RequestSpecification getBaseSpec () {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();


    }
}
