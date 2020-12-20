package edu.michaelszeler.homebudget.HomeBudgetServer.service;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyCategoryDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyDTO;

import java.util.List;

public interface StrategyService {
    List<StrategyDTO> getCurrentStrategies();
    List<StrategyDTO> getArchiveStrategies();
    List<StrategyCategoryDTO> getCategories();
    StrategyDTO postStrategy(StrategyDTO strategyDTO);
    StrategyDTO deleteStrategy(Integer id);
}
