package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.Budget;
import edu.michaelszeler.homebudget.server.entity.RegularExpenseInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RegularExpenseInstallmentRepository extends JpaRepository<RegularExpenseInstallment, Integer>, JpaSpecificationExecutor<RegularExpenseInstallment> {
    List<RegularExpenseInstallment> deleteAllByBudget(Budget budget);
}