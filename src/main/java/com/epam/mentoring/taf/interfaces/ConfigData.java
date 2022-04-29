package com.epam.mentoring.taf.interfaces;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/test/resources/config.properties")
public interface ConfigData extends Config {

    @Key("driver.type")
    String driverType();

    @Key("url.api")
    String apiUrl();

    @Key("url.ui")
    String uiUrl();

    @Key("wait.timeout.seconds")
    Integer waitTimeoutInSeconds();
}
