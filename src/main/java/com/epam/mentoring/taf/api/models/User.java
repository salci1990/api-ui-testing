package com.epam.mentoring.taf.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Nullable
    @JsonIgnore
    String type;

    @JsonProperty("email")
    String email;

    @JsonProperty("username")
    String username;

    @JsonProperty("password")
    String password;
}
