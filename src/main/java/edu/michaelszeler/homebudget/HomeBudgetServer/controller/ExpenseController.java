package edu.michaelszeler.homebudget.HomeBudgetServer.controller;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.ExpenseCategoryDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.RegularExpenseDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<RegularExpenseDTO> getRegularExpenses() {
        return expenseService.getCurrentExpenses();
    }

    @GetMapping("/categories")
    public List<ExpenseCategoryDTO> getExpenseCategories() {
        return expenseService.getCategories();
    }

    @PostMapping
    public RegularExpenseDTO postRegularExpense(@RequestBody RegularExpenseDTO regularExpenseDTO) {
        return expenseService.postExpense(regularExpenseDTO);
    }

    @DeleteMapping
    public RegularExpenseDTO deleteRegularExpense(@RequestBody RegularExpenseDTO regularExpenseDTO) {
        return expenseService.deleteExpense(regularExpenseDTO);
    }
}
