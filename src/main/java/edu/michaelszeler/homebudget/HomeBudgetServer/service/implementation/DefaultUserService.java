package edu.michaelszeler.homebudget.HomeBudgetServer.service.implementation;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Security;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.User;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.UserDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.SecurityRepository;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.UserRepository;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository,SecurityRepository securityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = (principal instanceof UserDetails) ? ((UserDetails)principal).getUsername() : principal.toString();

        List<User> users = userRepository.findAllByLogin(login);

        if (users.size() != 1) {
            throw new IllegalArgumentException("incorrect username");
        }

        return new UserDTO(users.get(0));
    }

    @Override
    public UserDTO postUser(UserDTO user) {
        if (!user.allSetForPost()) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        if (!userRepository.findAllByLogin(user.getLogin()).isEmpty()) {
            throw new IllegalArgumentException("username is already taken");
        }

        User userToPost = new User(0, user.getLogin(), passwordEncoder.encode(user.getPassword()), user.getFirstName(), user.getLastName());

        User savedUser = userRepository.save(userToPost);

        Security security = new Security(0, savedUser, "user", true);
        securityRepository.save(security);

        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO changePassword(UserDTO user) {

        if (user.getLogin() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        List<User> users = userRepository.findAllByLogin(user.getLogin());
        if (users.size() != 1) {
            throw new IllegalArgumentException("user not found");
        }

        User userToUpdate = users.get(0);
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));

        return new UserDTO(userRepository.save(userToUpdate));
    }
}