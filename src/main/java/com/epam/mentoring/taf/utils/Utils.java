package com.epam.mentoring.taf.utils;

import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.data.EndpointsData;
import com.epam.mentoring.taf.data.datafilemanagers.YamlDataFileManager;
import com.epam.mentoring.taf.interfaces.ConfigData;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final ConfigData CONFIG_DATA = ConfigFactory.create(ConfigData.class);
    public static final EndpointsData ENDPOINTS_DATA = YamlDataFileManager.readDataFromFile(CONFIG_DATA.endpointsFile(), EndpointsData.class);
    public static final UserDTO USER_DATA = YamlDataFileManager.readDataFromFile(CONFIG_DATA.userData(), UserDTO.class);

    public static synchronized int generateRandomNumber() {
        LOGGER.info("Generate random number.");
        return (int) (Math.random() * 1000);
    }

    public static String generateRandomString(String username) {
        String usernameWithUniqueId = username + generateRandomNumber();
        LOGGER.info("New random string was generated.", usernameWithUniqueId);
        return usernameWithUniqueId;
    }

    public static String generateRandomEmail(String email) {
        String emailWithUniqueId = email.replace("@", "." + generateRandomNumber() + "@");
        LOGGER.info("New random string was generated.", emailWithUniqueId);
        return emailWithUniqueId;
    }

    public static User getUserData(String userType) {
        return USER_DATA
                .getUsers()
                .stream()
                .filter(user -> userType.equals(user.getType()))
                .findAny()
                .orElse(null);
    }
}
