package edu.michaelszeler.homebudget.server.service;

import edu.michaelszeler.homebudget.server.model.StrategyCategoryDTO;
import edu.michaelszeler.homebudget.server.model.StrategyDTO;

import java.util.List;

public interface StrategyService {
    List<StrategyDTO> getCurrentStrategies();
    List<StrategyDTO> getArchiveStrategies();
    List<StrategyCategoryDTO> getCategories();
    StrategyDTO postStrategy(StrategyDTO strategyDTO);
    StrategyDTO deleteStrategy(Integer id);
}
