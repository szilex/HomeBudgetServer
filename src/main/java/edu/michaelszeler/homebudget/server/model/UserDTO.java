package edu.michaelszeler.homebudget.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.michaelszeler.homebudget.server.entity.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String login;
    private String password;
    private String firstName;
    private String lastName;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String firstName, String lastName) {
        this.login = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean allSetForPost() {
        return login != null && password != null && firstName != null && lastName != null;
    }
}