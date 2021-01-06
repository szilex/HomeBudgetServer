package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.StrategyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StrategyCategoryRepository extends JpaRepository<StrategyCategory, Integer>, JpaSpecificationExecutor<StrategyCategory> {
    List<StrategyCategory> findAllByName(String name);
}