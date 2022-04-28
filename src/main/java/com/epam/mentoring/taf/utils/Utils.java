package com.epam.mentoring.taf.utils;

import com.epam.mentoring.taf.interfaces.ConfigData;
import org.aeonbits.owner.ConfigFactory;

public class Utils {

    public static final ConfigData CONFIG_DATA = ConfigFactory.create(ConfigData.class);
}
