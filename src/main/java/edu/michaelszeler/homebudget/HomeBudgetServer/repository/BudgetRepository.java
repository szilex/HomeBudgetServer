package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Budget;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer>, JpaSpecificationExecutor<Budget> {
    List<Budget> findAllByUser(User user);
}