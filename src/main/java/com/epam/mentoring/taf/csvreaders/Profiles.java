package com.epam.mentoring.taf.csvreaders;

import com.epam.mentoring.taf.data.datafilemanagers.CSVDataFileManager;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Profiles extends CSVDataFileManager {

    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "type")
    private String type;
}
