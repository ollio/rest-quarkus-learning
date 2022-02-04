package org.acme.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.rest.model.User;
import org.acme.rest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class UserResourceTest {


    private static final Integer USER_ID = 4711;
    User testUser;

    @Inject
    UserResource userResource;

    @BeforeEach
    void setUp() {
        userResource.repository = mock(UserRepository.class);
        testUser = new User(USER_ID);
        testUser.setName("John");
        testUser.setSurname("Doe");
        testUser.setEmail("john.doe@example.com");
        Mockito.when(userResource.repository.getById(USER_ID)).thenReturn(testUser);

    }

    @Test
    public void create_user() {
        // given
        Mockito.when(userResource.repository.create()).thenReturn(testUser);

        // when
        final String location = given()
                .when().post("/api/users")
                .then()
                .statusCode(201)
                .extract().header("Location");

        // then
        assertThat(location).matches(".*\\/api\\/users\\/\\d{1,4}");
    }

    @Test
    public void put_user() {
        // given
        given()

                // when
                .contentType("application/json")
                .body(testUser)
                .when()
                .put("/api/users/" + USER_ID)

                // then
                .then()
                .statusCode(200)
                .body("name", equalTo(testUser.getName()))
                .body("surname", equalTo(testUser.getSurname()))
                .body("email", equalTo(testUser.getEmail()));
    }

    @Test
    public void get_user() {
        // given
        given()

                // when
                .when().get("/api/users/" + USER_ID)

                // then
                .then()
                .statusCode(200)
                .body("name", equalTo(testUser.getName()))
                .body("surname", equalTo(testUser.getSurname()))
                .body("email", equalTo(testUser.getEmail()));
    }

    @Test
    void delete_user() {
        // given
        given()

                // when
                .when().delete("/api/users/" + USER_ID)

                // then
                .then()
                .statusCode(204);

        verify(userResource.repository).delete(USER_ID);
    }

    private String userLocation() {
        return given()
                .when().post("/api/users")
                .then()
                .statusCode(201)
                .extract().header("Location");
    }


}