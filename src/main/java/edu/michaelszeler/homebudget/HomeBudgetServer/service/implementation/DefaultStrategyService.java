package edu.michaelszeler.homebudget.HomeBudgetServer.service.implementation;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Strategy;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.StrategyCategory;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.User;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyCategoryDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.model.StrategyDTO;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.StrategyCategoryRepository;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.StrategyRepository;
import edu.michaelszeler.homebudget.HomeBudgetServer.repository.UserRepository;
import edu.michaelszeler.homebudget.HomeBudgetServer.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultStrategyService implements StrategyService {

    private final UserRepository userRepository;
    private final StrategyRepository strategyRepository;
    private final StrategyCategoryRepository strategyCategoryRepository;

    @Autowired
    public DefaultStrategyService(UserRepository userRepository, StrategyRepository strategyRepository, StrategyCategoryRepository strategyCategoryRepository) {
        this.userRepository = userRepository;
        this.strategyRepository = strategyRepository;
        this.strategyCategoryRepository = strategyCategoryRepository;
    }

    @Override
    public List<StrategyDTO> getCurrentStrategies() {

        User user = getUser();
        List<Strategy> strategies = strategyRepository.findAllByUser(user);
        List<StrategyDTO> dtos = strategies.stream()
                .filter(strategy -> {
                    LocalDate start = strategy.getStartDate();
                    int months = strategy.getMonths();
                    return !LocalDate.now().isBefore(start) && !LocalDate.now().isAfter(start.plusMonths(months));
                })
                .map(StrategyDTO::new)
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            throw new NoSuchElementException("no strategies found");
        }

        return dtos;
    }

    @Override
    public List<StrategyDTO> getArchiveStrategies() {

        User user = getUser();
        List<Strategy> strategies = strategyRepository.findAllByUser(user);
        List<StrategyDTO> dtos = strategies.stream()
                .filter(strategy -> {
                    LocalDate start = strategy.getStartDate();
                    int months = strategy.getMonths();
                    return LocalDate.now().isAfter(start.plusMonths(months));
                })
                .map(StrategyDTO::new)
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            throw new NoSuchElementException("no strategies found");
        }

        return dtos;
    }

    @Override
    public List<StrategyCategoryDTO> getCategories() {
        return strategyCategoryRepository.findAll().stream().map(StrategyCategoryDTO::new).collect(Collectors.toList());
    }

    @Override
    public StrategyDTO postStrategy(StrategyDTO strategy) {
        if (!strategy.allSetForPost()) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        User user = getUser();

        Strategy strategyToPost = new Strategy();
        strategyToPost.setId(0);
        strategyToPost.setUser(user);
        strategyToPost.setCategory(getCategory(strategy.getCategory()));
        strategyToPost.setName(strategy.getName());
        strategyToPost.setDescription(strategy.getDescription());
        strategyToPost.setGoal(strategy.getGoal());
        strategyToPost.setStartDate(strategy.getStartDate());
        strategyToPost.setMonths(strategy.getMonths());

        return new StrategyDTO(strategyRepository.save(strategyToPost));
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

    private StrategyCategory getCategory(String category) {
        List<StrategyCategory> categories = strategyCategoryRepository.findAllByName(category);
        if (categories.size() != 1) {
            throw new IllegalArgumentException("invalid category name");
        }
        return categories.get(0);
    }
}
