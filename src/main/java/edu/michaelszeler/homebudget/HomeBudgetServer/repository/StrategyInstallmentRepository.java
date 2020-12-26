package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Budget;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.StrategyInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyInstallmentRepository extends JpaRepository<StrategyInstallment, Integer>, JpaSpecificationExecutor<StrategyInstallment> {
    List<StrategyInstallment> deleteAllByBudget(Budget budget);
}