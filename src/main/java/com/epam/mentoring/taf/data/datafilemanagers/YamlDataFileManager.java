package com.epam.mentoring.taf.data.datafilemanagers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.epam.mentoring.taf.utils.Utils.CONFIG_DATA;

public class YamlDataFileManager implements DataFileManager {

    private static final String YAML_FILE_PATH = CONFIG_DATA.pathToYamlFolder();
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T> T readDataFromFile(String fileName, Class<T> type) {
        LOGGER.info("Read data from file.", fileName);
        final String PATH_TO_FILE = YAML_FILE_PATH + fileName;
        Yaml yaml = new Yaml(new Constructor(type));
        T data = null;

        try (InputStream inputStream = new FileInputStream(PATH_TO_FILE)) {
            data = yaml.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Reading data from file, error IOException competent to perform");
            e.printStackTrace();
        }
        LOGGER.trace("Loaded data from file.", fileName);
        return data;
    }
}
