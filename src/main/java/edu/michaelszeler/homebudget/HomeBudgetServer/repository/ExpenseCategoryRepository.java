package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer>, JpaSpecificationExecutor<ExpenseCategory> {
    List<ExpenseCategory> findAllByName(String name);
}