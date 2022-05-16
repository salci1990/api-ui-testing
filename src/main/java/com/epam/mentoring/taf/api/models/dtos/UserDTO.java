package com.epam.mentoring.taf.api.models.dtos;

import com.epam.mentoring.taf.api.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("user")
    User user;

    @JsonIgnore
    List<User> users;

    public UserDTO(User user) {
        this.user = user;
    }
}
