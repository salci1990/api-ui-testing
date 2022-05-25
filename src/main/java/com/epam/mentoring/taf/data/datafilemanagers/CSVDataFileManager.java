package com.epam.mentoring.taf.data.datafilemanagers;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CSVDataFileManager implements DataFileManager {

    private static final Logger LOGGER = LogManager.getLogger();
    public <T> List<T> readDataFromFile(String fileName, Class<T> type) {
        LOGGER.info("Read data from file.");
        try {
            List<T> listOfObjects = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(type)
                    .build()
                    .parse();
        LOGGER.info("Return list of objects.");
            return listOfObjects;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
