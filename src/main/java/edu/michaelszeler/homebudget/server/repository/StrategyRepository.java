package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.Strategy;
import edu.michaelszeler.homebudget.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, Integer>, JpaSpecificationExecutor<Strategy> {
    List<Strategy> findAllByUser(User user);
    List<Strategy> findAllByIdIn(List<Integer> ids);
}