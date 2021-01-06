package edu.michaelszeler.homebudget.server.service.implementation;

import edu.michaelszeler.homebudget.server.entity.User;
import edu.michaelszeler.homebudget.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }
}
