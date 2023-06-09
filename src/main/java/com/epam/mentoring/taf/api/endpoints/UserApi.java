package com.epam.mentoring.taf.api.endpoints;

import com.epam.mentoring.taf.api.RESTCore;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.utils.Utils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserApi extends RESTCore {

    private static final String ENDPOINTS_DATA = Utils.ENDPOINTS_DATA.getLogin();
    private static final Logger LOGGER = LogManager.getLogger();

    public Response createNewUser(UserDTO userDTO) {
        LOGGER.trace("Prepered request to send.");
        return prepareRequest()
                .body(userDTO)
                .post(ENDPOINTS_DATA);
    }
}
