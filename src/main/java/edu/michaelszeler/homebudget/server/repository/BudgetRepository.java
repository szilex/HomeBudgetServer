package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.Budget;
import edu.michaelszeler.homebudget.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer>, JpaSpecificationExecutor<Budget> {
    List<Budget> findAllByUser(User user);
}