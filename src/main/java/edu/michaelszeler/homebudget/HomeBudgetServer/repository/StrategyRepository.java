package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Strategy;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, Integer>, JpaSpecificationExecutor<Strategy> {
    List<Strategy> findAllByUser(User user);
    List<Strategy> findAllByIdIn(List<Integer> ids);
}