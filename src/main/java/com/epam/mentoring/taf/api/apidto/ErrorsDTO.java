package com.epam.mentoring.taf.api.apidto;

import com.epam.mentoring.taf.api.models.Errors;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorsDTO {

    @JsonProperty("errors")
    Errors errors;

    public ErrorsDTO(Errors errors) {
        this.errors = errors;
    }
}
