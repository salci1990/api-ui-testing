package com.epam.mentoring.taf.api.endpoints;

import com.epam.mentoring.taf.api.RESTCore;
import com.epam.mentoring.taf.utils.Utils;
import io.restassured.response.Response;

public class SignUpApi extends RESTCore {

    private static final String SIGNUP_ENDPOINT = Utils.SIGNUP_DATA.getSignup();

    public Response signUpUser(String body) {
        return prepareRequest()
                .body(body)
                .post(SIGNUP_ENDPOINT);
    }
}
