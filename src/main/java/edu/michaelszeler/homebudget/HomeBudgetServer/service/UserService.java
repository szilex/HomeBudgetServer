package edu.michaelszeler.homebudget.HomeBudgetServer.service;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.UserDTO;

public interface UserService {
    UserDTO getUser();
    UserDTO postUser(UserDTO user);
    UserDTO changePassword(UserDTO user);
}