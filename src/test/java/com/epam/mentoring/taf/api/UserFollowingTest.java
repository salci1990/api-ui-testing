package com.epam.mentoring.taf.api;

import com.epam.mentoring.taf.AbstractTest;
import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.LogInApi;
import com.epam.mentoring.taf.api.endpoints.ProfilesApi;
import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.csvreaders.Profiles;
import com.epam.mentoring.taf.data.datafilemanagers.CSVDataFileManager;
import com.epam.mentoring.taf.ui.pageobjects.listeners.TestListener;
import com.epam.mentoring.taf.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Listeners({TestListener.class})
public class UserFollowingTest extends AbstractTest {
    private UserDTO userDTO;
    private User existingUser;
    private LogInApi logInApi;
    private ProfilesApi profilesApi;
    private String token;
    private String fileName;
    private String type;
    private String type2;

    @BeforeClass
    public void setupTest() {
        existingUser = Utils.getUserData("existing");
        profilesApi = new ProfilesApi();
        logInApi = new LogInApi();

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(existingUser.getUsername())
                        .withEmail(existingUser.getEmail())
                        .withPassword(existingUser.getPassword())
                        .build()
        );

        token = logInApi.getLoginToken(userDTO);
        fileName = Utils.CONFIG_DATA.profileData();
        type = "existing";
        type2 = "unexisting";
        profilesApi.addHeader("Authorization", "Token " + token);
    }

    @DataProvider(name = "profileData")
    public <T> Iterator<Object[]> dataProvider() {
        List<Profiles> profiles = new CSVDataFileManager().readDataFromFile(fileName, Profiles.class);
        return profiles.stream()
                .filter(profile -> type.equals(profile.getType()))
                .map(profile -> new Object[]{profile})
                .iterator();
    }

    @DataProvider(name = "profileData2")
    public <T> Iterator<Object[]> dataProvider2() {
        List<Profiles> profiles = new CSVDataFileManager().readDataFromFile(fileName, Profiles.class);
        return profiles.stream()
                .filter(profile -> type2.equals(profile.getType()))
                .map(profile -> new Object[]{profile})
                .iterator();
    }

    @Test(dataProvider = "profileData")
    public void getProfileTest(Profiles profile) {
        profilesApi.getProfiles(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .body("profile.username", is("Gerome"));
    }

    @Test(dataProvider = "profileData")
    public void followNotLoggedUserTest(Profiles profile) {
        ProfilesApi profilesApi1 = new ProfilesApi();
        profilesApi1.followUser(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(401)
                .body("status", is("error"))
                .body("message", is("missing authorization credentials"));
    }

    @Test(dataProvider = "profileData")
    public void followUserTest(Profiles profile) {
        profilesApi.followUser(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .body("profile.username", is("Gerome"))
                .body("profile.following", equalTo(true));
    }

    @Test(dataProvider = "profileData2")
    public void incorrectFollowUserTest(Profiles profile) {
        profilesApi.followUser(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test(dependsOnMethods = "followUserTest", dataProvider = "profileData")
    public void unfollowUserTest(Profiles profile) {
        profilesApi.unfollowUser(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .body("profile.username", is("Gerome"))
                .body("profile.following", equalTo(false));
    }

    @Test(dataProvider = "profileData")
    public void incorrectUnfollowUserTest(Profiles profile) {
        profilesApi.followUser(profile.getUsername())
                .then()
                .assertThat()
                .statusCode(200)
                .body("profile.following", equalTo(true));
    }
}
