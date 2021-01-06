package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.Budget;
import edu.michaelszeler.homebudget.server.entity.CustomExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CustomExpenseRepository extends JpaRepository<CustomExpense, Long>, JpaSpecificationExecutor<CustomExpense> {
    List<CustomExpense> findAllByIdIn(List<Integer> ids);
    List<CustomExpense> deleteAllByBudget(Budget budget);
}