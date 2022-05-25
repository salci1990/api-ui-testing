package com.epam.mentoring.taf.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EndpointsData {

    private String users;
    private String login;
    private String profiles;
}
