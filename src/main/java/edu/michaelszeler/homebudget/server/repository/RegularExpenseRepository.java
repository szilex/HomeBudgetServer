package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.RegularExpense;
import edu.michaelszeler.homebudget.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RegularExpenseRepository extends JpaRepository<RegularExpense, Integer>, JpaSpecificationExecutor<RegularExpense> {
    List<RegularExpense> findAllByUser(User user);
    List<RegularExpense> findAllByIdIn(List<Integer> ids);
}