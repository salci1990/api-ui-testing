package com.epam.mentoring.taf.data.datafilemanagers;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.epam.mentoring.taf.utils.Utils.CONFIG_DATA;

public class YamlDataFileManager implements DataFileManager {

    private static final String YAML_FILE_PATH = CONFIG_DATA.pathToYamlFolder();

    public static <T> T readDataFromFile(String fileName, Class<T> type) {
        final String PATH_TO_FILE = YAML_FILE_PATH + fileName;
        Yaml yaml = new Yaml(new Constructor(type));
        T data = null;

        try (InputStream inputStream = new FileInputStream(PATH_TO_FILE)) {
            data = yaml.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
