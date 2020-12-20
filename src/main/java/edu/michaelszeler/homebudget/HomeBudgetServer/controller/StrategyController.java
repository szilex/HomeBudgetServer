package edu.michaelszeler.homebudget.HomeBudgetServer.controller;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyCategoryDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strategy")
public class StrategyController {

    private final StrategyService strategyService;

    @Autowired
    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping
    public List<StrategyDTO> getCurrentStrategies() {
        return strategyService.getCurrentStrategies();
    }

    @GetMapping("/categories")
    public List<StrategyCategoryDTO> getStrategyCategories() {
        return strategyService.getCategories();
    }

    @GetMapping("/archive")
    public List<StrategyDTO> getArchiveStrategies() {
        return strategyService.getArchiveStrategies();
    }

    @PostMapping
    public StrategyDTO postStrategy(@RequestBody StrategyDTO strategy) {
        return strategyService.postStrategy(strategy);
    }

    @DeleteMapping
    public StrategyDTO deleteStrategy(@RequestBody StrategyDTO strategy) {
        return strategyService.deleteStrategy(strategy);
    }
}
