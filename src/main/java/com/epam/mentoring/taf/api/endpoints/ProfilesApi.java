package com.epam.mentoring.taf.api.endpoints;

import com.epam.mentoring.taf.api.RESTCore;
import com.epam.mentoring.taf.utils.Utils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfilesApi extends RESTCore {

    private static final String PROFILES_ENDPOINT = Utils.ENDPOINTS_DATA.getProfiles();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FOLLOW_ENDPOINT = "/follow";

    public Response getProfiles(String profile) {
        LOGGER.info("Prepare GET request.");
        return prepareRequest()
                .get(PROFILES_ENDPOINT + "/" + profile);
    }

    public Response followUser(String profile) {
        LOGGER.info("Prepare POST request.");
        return prepareRequest()
                .post(PROFILES_ENDPOINT + "/" + profile + FOLLOW_ENDPOINT);
    }

    public Response unfollowUser(String profile) {
        LOGGER.info("Prepare DELETE request.");
        return prepareRequest()
                .delete(PROFILES_ENDPOINT + "/" + profile + FOLLOW_ENDPOINT);
    }
}