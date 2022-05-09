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

    @Key("path.to.yaml.folder")
    String pathToYamlFolder();

    @Key("endpoints.file")
    String endpointsFile();

    @Key("user.data")
    Object userData();
}
