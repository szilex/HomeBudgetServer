package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.StrategyInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StrategyInstallmentRepository extends JpaRepository<StrategyInstallment, Integer>, JpaSpecificationExecutor<StrategyInstallment> {

}