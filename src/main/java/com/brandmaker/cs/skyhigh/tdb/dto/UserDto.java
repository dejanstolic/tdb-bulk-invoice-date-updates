package com.brandmaker.cs.skyhigh.tdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO Object for user credentials.
 * @author PC
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Integer id;

    private String firstName;

    private String fullName;

    private String lastName;

    private String email;

    private String login;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
