package edu.michaelszeler.homebudget.server.service;

import edu.michaelszeler.homebudget.server.model.UserDTO;

public interface UserService {
    UserDTO getUser();
    UserDTO postUser(UserDTO user);
    UserDTO changePassword(UserDTO user);
}