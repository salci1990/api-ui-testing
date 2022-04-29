package com.epam.mentoring.taf.api.endpoints;

import com.epam.mentoring.taf.api.RESTCore;
import com.epam.mentoring.taf.utils.Utils;
import io.restassured.response.Response;

public class LogInApi extends RESTCore {

    private static final String LOGIN_ENDPOINT = Utils.ENDPOINTS_DATA.getLogin();

    public Response logInUser(String body) {
        return prepareRequest()
                .body(body)
                .post(LOGIN_ENDPOINT);
    }
}
