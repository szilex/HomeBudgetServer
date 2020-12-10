package edu.michaelszeler.homebudget.HomeBudgetServer.service;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.BudgetDTO;

import java.util.List;

public interface BudgetService {
    BudgetDTO getCurrentBudget();
    List<BudgetDTO> getArchiveBudgets();
    BudgetDTO postBudget(BudgetDTO budget);
}
