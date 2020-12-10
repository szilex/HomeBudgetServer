package edu.michaelszeler.homebudget.HomeBudgetServer.controller;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.BudgetDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public BudgetDTO getCurrentBudget() {
        return budgetService.getCurrentBudget();
    }

    @GetMapping("/archive")
    public List<BudgetDTO> getArchiveBudgets() {
        return budgetService.getArchiveBudgets();
    }

    @PostMapping
    public BudgetDTO postBudget(@RequestBody BudgetDTO budget) {
        return budgetService.postBudget(budget);
    }
}
