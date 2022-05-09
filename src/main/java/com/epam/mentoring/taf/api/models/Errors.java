package com.epam.mentoring.taf.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Errors {

    @JsonProperty("email")
    List<String> email;

    @JsonProperty("username")
    List<String> username;
}
