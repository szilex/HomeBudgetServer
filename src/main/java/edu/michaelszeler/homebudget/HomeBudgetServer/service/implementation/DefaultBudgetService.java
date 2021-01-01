package edu.michaelszeler.homebudget.HomeBudgetServer.service.implementation;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.*;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.BudgetDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.RegularExpenseDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.*;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.BudgetService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultBudgetService implements BudgetService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final CustomExpenseRepository customExpenseRepository;
    private final RegularExpenseRepository regularExpenseRepository;
    private final RegularExpenseInstallmentRepository regularExpenseInstallmentRepository;
    private final StrategyRepository strategyRepository;
    private final StrategyInstallmentRepository strategyInstallmentRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public DefaultBudgetService(UserRepository userRepository, BudgetRepository budgetRepository, CustomExpenseRepository customExpenseRepository, RegularExpenseRepository regularExpenseRepository, RegularExpenseInstallmentRepository regularExpenseInstallmentRepository, StrategyRepository strategyRepository, StrategyInstallmentRepository strategyInstallmentRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
        this.customExpenseRepository = customExpenseRepository;
        this.regularExpenseRepository = regularExpenseRepository;
        this.regularExpenseInstallmentRepository = regularExpenseInstallmentRepository;
        this.strategyRepository = strategyRepository;
        this.strategyInstallmentRepository = strategyInstallmentRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public BudgetDTO getCurrentBudget() {

        User user = getUser();

        List<Budget> budgetList = budgetRepository.findAllByUser(user);
        if (budgetList.isEmpty()) {
            throw new NoSuchElementException("no budget found");
        }

        for (Budget budget : budgetList) {
            LocalDate date = budget.getDate();
            if (date.getYear() == LocalDate.now().getYear() && date.getMonth() == LocalDate.now().getMonth()) {
                return new BudgetDTO(budget);
            }
        }

        throw new NoSuchElementException("no current budget found");
    }

    @Override
    public List<BudgetDTO> getArchiveBudgets() {

        User user = getUser();

        List<Budget> budgetList = budgetRepository.findAllByUser(user);
        if (budgetList.isEmpty()) {
            throw new NoSuchElementException("no budget found");
        }

        return budgetList.stream()
                .filter(budget -> {
                    LocalDate date = budget.getDate();
                    LocalDate now = LocalDate.now();
                    return !(date.getYear() == now.getYear() && date.getMonth() == now.getMonth());
                })
                .map(BudgetDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO postBudget(BudgetDTO budget) {
        if (!budget.allSetForPost()) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        User user = getUser();
        Budget budgetToPost = new Budget();
        budgetToPost.setId(0);
        budgetToPost.setUser(user);
        budgetToPost.setDate(budget.getDate());
        budgetToPost.setIncome(budget.getIncome());

        long counter = budgetRepository.findAllByUser(user).stream()
                .filter(budgetEntry -> budgetEntry.getDate().getYear() == budgetToPost.getDate().getYear() && budgetEntry.getDate().getMonth() == budgetToPost.getDate().getMonth())
                .count();

        if (counter > 0) {
            throw new IllegalArgumentException("budget for this month is already defined");
        }

        Budget budgetToUpdate = budgetRepository.save(budgetToPost);

        List<CustomExpense> customExpenses = getCustomExpenses(budget, budgetToUpdate);
        budgetToUpdate.setCustomExpenseList(customExpenses);
        budgetRepository.save(budgetToUpdate);

        List<StrategyInstallment> strategyInstallments = getStrategyInstallments(budget, budgetToUpdate);
        budgetToUpdate.setStrategyInstallments(strategyInstallments);
        budgetRepository.save(budgetToUpdate);


        List<RegularExpenseInstallment> regularExpenseInstallments = getRegularExpenseInstallments(budget, budgetToUpdate);
        budgetToUpdate.setRegularExpenseInstallments(regularExpenseInstallments);
        budgetRepository.save(budgetToUpdate);

        return new BudgetDTO(budgetToUpdate);
    }

    @Override
    public BudgetDTO deleteBudget(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("budget id not specified");
        }

        Optional<Budget> optionalBudget = budgetRepository.findById(id);
        if (optionalBudget.isEmpty()) {
            throw new IllegalArgumentException("budget not found");
        }

        Budget budget = optionalBudget.get();
        strategyInstallmentRepository.deleteAllByBudget(budget);
        regularExpenseInstallmentRepository.deleteAllByBudget(budget);
        customExpenseRepository.deleteAllByBudget(budget);
        budgetRepository.delete(budget);

        return null;
    }

    @NotNull
    private List<CustomExpense> getCustomExpenses(BudgetDTO budget, Budget budgetToPost) {
        List<ExpenseCategory> expenseCategories = expenseCategoryRepository.findAll();
        Optional<ExpenseCategory> optionalDefaultCategory = expenseCategories.stream()
                .filter(expenseCategory -> expenseCategory.getId() == 1)
                .findFirst();
        ExpenseCategory defaultCategory = optionalDefaultCategory.orElse(null);

        if (budget.getCustomExpenses() == null) {
            return Collections.emptyList();
        }

        return budget.getCustomExpenses().stream()
                .map(customExpenseDTO -> {
                    List<ExpenseCategory> categories = expenseCategories.stream()
                            .filter(expenseCategory -> expenseCategory.getName().equals(customExpenseDTO.getCategory()))
                            .collect(Collectors.toList());
                    ExpenseCategory category = categories.size() == 1 ? categories.get(0) : defaultCategory;
                    return new CustomExpense(0, category, budgetToPost, customExpenseDTO.getName(), customExpenseDTO.getAmount(), customExpenseDTO.getDate(), customExpenseDTO.getMonths());
                })
                .collect(Collectors.toList());
    }

    @NotNull
    private List<RegularExpenseInstallment> getRegularExpenseInstallments(BudgetDTO budget, Budget budgetToPost) {
        if (budget.getRegularExpenses() == null) {
            return Collections.emptyList();
        }

        List<Integer> regularExpenseIds = budget.getRegularExpenses().stream()
                .map(RegularExpenseDTO::getId)
                .collect(Collectors.toList());
        List<RegularExpense> regularExpenses = regularExpenseRepository.findAllByIdIn(regularExpenseIds);
        return regularExpenses.stream()
                .map(regularExpense -> new RegularExpenseInstallment(0, budgetToPost, regularExpense))
                .collect(Collectors.toList());
    }

    @NotNull
    private List<StrategyInstallment> getStrategyInstallments(BudgetDTO budget, Budget budgetToPost) {
        if (budget.getRegularExpenses() == null) {
            return Collections.emptyList();
        }

        List<Integer> strategyIds = budget.getStrategies().stream()
                .map(StrategyDTO::getId)
                .collect(Collectors.toList());
        List<Strategy> strategies = strategyRepository.findAllByIdIn(strategyIds);
        return strategies.stream()
                .map(strategy -> new StrategyInstallment(0, budgetToPost, strategy, strategy.getGoal().divideToIntegralValue(new BigDecimal(strategy.getMonths()))))
                .collect(Collectors.toList());
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (principal instanceof UserDetails) ? ((UserDetails)principal).getUsername() : principal.toString();

        List<User> users = userRepository.findAllByLogin(username);
        if (users.size() != 1) {
            throw new IllegalArgumentException("user not found");
        }

        return users.get(0);
    }
}
