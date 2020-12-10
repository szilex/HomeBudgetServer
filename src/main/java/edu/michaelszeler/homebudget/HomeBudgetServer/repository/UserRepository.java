package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findAllByLogin(String login);
}