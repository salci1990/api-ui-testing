package com.epam.mentoring.taf.utils;

import com.epam.mentoring.taf.data.EndpointsData;
import com.epam.mentoring.taf.data.datafilemanagers.YamlDataFileManager;
import com.epam.mentoring.taf.interfaces.ConfigData;
import org.aeonbits.owner.ConfigFactory;

public class Utils {

    public static final ConfigData CONFIG_DATA = ConfigFactory.create(ConfigData.class);
    public static final EndpointsData ENDPOINTS_DATA = YamlDataFileManager.readDataFromFile(CONFIG_DATA.endpointsFile(), EndpointsData.class);
}
