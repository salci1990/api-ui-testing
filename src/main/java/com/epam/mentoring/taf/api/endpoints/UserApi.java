package com.epam.mentoring.taf.api.endpoints;

import com.epam.mentoring.taf.api.RESTCore;
import com.epam.mentoring.taf.api.apidto.UserDTO;
import com.epam.mentoring.taf.utils.Utils;
import io.restassured.response.Response;

public class UserApi extends RESTCore {

    private static final String ENDPOINTS_DATA = Utils.ENDPOINTS_DATA.getLogin();

    public Response createNewUser(UserDTO userDTO, boolean... shouldRequestFailur) {
        return prepareRequest()
                .body(userDTO)
                .post(ENDPOINTS_DATA);
    }
}