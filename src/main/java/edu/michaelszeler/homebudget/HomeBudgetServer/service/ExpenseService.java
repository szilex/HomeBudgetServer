package edu.michaelszeler.homebudget.HomeBudgetServer.service;

import edu.michaelszeler.homebudget.HomeBudgetServer.model.ExpenseCategoryDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.RegularExpenseDTO;

import java.util.List;

public interface ExpenseService {

    List<RegularExpenseDTO> getCurrentExpenses();
    List<ExpenseCategoryDTO> getCategories();
    RegularExpenseDTO postExpense(RegularExpenseDTO regularExpenseDTO);
    RegularExpenseDTO deleteExpense(RegularExpenseDTO regularExpenseDTO);
}
