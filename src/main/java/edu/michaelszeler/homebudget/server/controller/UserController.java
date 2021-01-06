package edu.michaelszeler.homebudget.server.controller;

import edu.michaelszeler.homebudget.server.model.UserDTO;
import edu.michaelszeler.homebudget.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public UserDTO getUser() {
        return userService.getUser();
    }

    @PostMapping("/register")
    public UserDTO postUser(@RequestBody UserDTO user) {
        return userService.postUser(user);
    }

    @PutMapping("/changePassword")
    public UserDTO changePassword(@RequestBody UserDTO user) {
        return userService.changePassword(user);
    }
}
