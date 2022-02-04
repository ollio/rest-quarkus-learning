package org.acme.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.acme.rest.UserResource.RESOURCE_URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonIgnore
    private final Integer id;

    @JsonProperty("_self")
    private final String rel;

    private String email;
    private String surname;
    private String name;

    public User() {
        this(null);
    }

    public User(Integer id) {
        this.id = id;
        this.rel = RESOURCE_URI + "/" + id;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
