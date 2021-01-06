package edu.michaelszeler.homebudget.server.controller;

import edu.michaelszeler.homebudget.server.model.BudgetDTO;
import edu.michaelszeler.homebudget.server.service.BudgetService;
import org.jetbrains.annotations.NotNull;
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

    @DeleteMapping
    public BudgetDTO deleteBudget(@RequestParam(name = "id") @NotNull Integer id) {
        return budgetService.deleteBudget(id);
    }
}
