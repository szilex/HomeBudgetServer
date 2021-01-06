package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByLogin(String login);
    List<User> findAllByLogin(String login);
}