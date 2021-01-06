package edu.michaelszeler.homebudget.server.service.implementation;

import edu.michaelszeler.homebudget.server.entity.ExpenseCategory;
import edu.michaelszeler.homebudget.server.entity.RegularExpense;
import edu.michaelszeler.homebudget.server.entity.User;
import edu.michaelszeler.homebudget.server.model.ExpenseCategoryDTO;
import edu.michaelszeler.homebudget.server.model.RegularExpenseDTO;
import edu.michaelszeler.homebudget.server.repository.ExpenseCategoryRepository;
import edu.michaelszeler.homebudget.server.repository.RegularExpenseRepository;
import edu.michaelszeler.homebudget.server.repository.UserRepository;
import edu.michaelszeler.homebudget.server.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultExpenseService implements ExpenseService {

    private final UserRepository userRepository;
    private final RegularExpenseRepository regularExpenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    public DefaultExpenseService(UserRepository userRepository, RegularExpenseRepository regularExpenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.userRepository = userRepository;
        this.regularExpenseRepository = regularExpenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public List<RegularExpenseDTO> getCurrentExpenses() {
        User user = getUser();
        List<RegularExpense> regularExpenses = regularExpenseRepository.findAllByUser(user);
        List<RegularExpenseDTO> dtos = regularExpenses.stream()
                .filter(regularExpense -> {
                    LocalDate start = regularExpense.getStartDate();
                    int months = regularExpense.getMonths();
                    //return !LocalDate.now().isBefore(start) && !LocalDate.now().isAfter(start.plusMonths(months));
                    return !LocalDate.now().isAfter(start.plusMonths(months));
                })
                .filter(RegularExpense::getActive)
                .map(RegularExpenseDTO::new)
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            throw new NoSuchElementException("no regular expenses found");
        }

        return dtos;
    }

    @Override
    public List<ExpenseCategoryDTO> getCategories() {
        return expenseCategoryRepository.findAll().stream().map(ExpenseCategoryDTO::new).collect(Collectors.toList());
    }

    @Override
    public RegularExpenseDTO postExpense(RegularExpenseDTO regularExpenseDTO) {
        if (!regularExpenseDTO.allSetForPost()) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        User user = getUser();

        RegularExpense regularExpense = new RegularExpense();
        regularExpense.setId(0);
        regularExpense.setUser(user);
        regularExpense.setCategory(getCategory(regularExpenseDTO.getCategory()));
        regularExpense.setName(regularExpenseDTO.getName());
        regularExpense.setAmount(regularExpenseDTO.getAmount());
        regularExpense.setStartDate(regularExpenseDTO.getStartDate());
        regularExpense.setMonths(regularExpenseDTO.getMonths());
        regularExpense.setActive(true);

        return new RegularExpenseDTO(regularExpenseRepository.save(regularExpense));
    }

    @Override
    public RegularExpenseDTO deleteExpense(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Expense id not specified");
        }

        Optional<RegularExpense> regularExpense = regularExpenseRepository.findById(id);
        if (regularExpense.isEmpty() || !regularExpense.get().getActive()) {
            throw new IllegalArgumentException("Expense not found");
        }

        regularExpense.get().setActive(false);
        RegularExpense savedRegularExpense = regularExpenseRepository.save(regularExpense.get());

        return new RegularExpenseDTO(savedRegularExpense);
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

    private ExpenseCategory getCategory(String category) {
        List<ExpenseCategory> categories = expenseCategoryRepository.findAllByName(category);
        if (categories.size() != 1) {
            throw new IllegalArgumentException("invalid category name");
        }
        return categories.get(0);
    }
}
