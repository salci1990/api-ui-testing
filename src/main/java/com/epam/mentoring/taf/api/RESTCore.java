package com.epam.mentoring.taf.api;

import com.epam.mentoring.taf.utils.Utils;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RESTCore {

    private final RequestSpecification REQUEST_SPECIFICATION;
    private static final String API_URL = Utils.CONFIG_DATA.apiUrl();
    private Map<String, Object> restAssuredHeaders = new ConcurrentHashMap<>();

    public RESTCore() {
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification prepareRequest() {
        return RestAssured.given()
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured())
                .spec(REQUEST_SPECIFICATION)
                .headers(restAssuredHeaders)
                .baseUri(API_URL);
    }

    public void addHeader(String key, String value) {
        restAssuredHeaders.put(key, value);
    }
}
