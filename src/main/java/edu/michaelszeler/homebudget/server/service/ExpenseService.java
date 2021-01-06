package edu.michaelszeler.homebudget.server.service;

import edu.michaelszeler.homebudget.server.model.ExpenseCategoryDTO;
import edu.michaelszeler.homebudget.server.model.RegularExpenseDTO;

import java.util.List;

public interface ExpenseService {

    List<RegularExpenseDTO> getCurrentExpenses();
    List<ExpenseCategoryDTO> getCategories();
    RegularExpenseDTO postExpense(RegularExpenseDTO regularExpenseDTO);
    RegularExpenseDTO deleteExpense(Integer id);
}
