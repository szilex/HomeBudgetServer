package edu.michaelszeler.homebudget.server.service;

import edu.michaelszeler.homebudget.server.model.BudgetDTO;

import java.util.List;

public interface BudgetService {
    BudgetDTO getCurrentBudget();
    List<BudgetDTO> getArchiveBudgets();
    BudgetDTO postBudget(BudgetDTO budget);
    BudgetDTO deleteBudget(Integer id);
}
