package edu.michaelszeler.homebudget.server.service.implementation;

import edu.michaelszeler.homebudget.server.entity.Strategy;
import edu.michaelszeler.homebudget.server.entity.StrategyCategory;
import edu.michaelszeler.homebudget.server.entity.User;
import edu.michaelszeler.homebudget.server.model.StrategyCategoryDTO;
import edu.michaelszeler.homebudget.server.model.StrategyDTO;
import edu.michaelszeler.homebudget.server.repository.StrategyCategoryRepository;
import edu.michaelszeler.homebudget.server.repository.StrategyRepository;
import edu.michaelszeler.homebudget.server.repository.UserRepository;
import edu.michaelszeler.homebudget.server.service.StrategyService;
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
                    //return !LocalDate.now().isBefore(start) && !LocalDate.now().isAfter(start.plusMonths(months));
                    return !LocalDate.now().isAfter(start.plusMonths(months));
                })
                .filter(Strategy::getActive)
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
    public StrategyDTO postStrategy(StrategyDTO strategyDTO) {
        if (!strategyDTO.allSetForPost()) {
            throw new IllegalArgumentException("insufficient argument list");
        }

        User user = getUser();

        Strategy strategyToPost = new Strategy();
        strategyToPost.setId(0);
        strategyToPost.setUser(user);
        strategyToPost.setCategory(getCategory(strategyDTO.getCategory()));
        strategyToPost.setName(strategyDTO.getName());
        strategyToPost.setDescription(strategyDTO.getDescription());
        strategyToPost.setGoal(strategyDTO.getGoal());
        strategyToPost.setStartDate(strategyDTO.getStartDate());
        strategyToPost.setMonths(strategyDTO.getMonths());
        strategyToPost.setActive(true);

        return new StrategyDTO(strategyRepository.save(strategyToPost));
    }

    @Override
    public StrategyDTO deleteStrategy(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Strategy id not specified");
        }

        Optional<Strategy> strategy = strategyRepository.findById(id);
        if (strategy.isEmpty() || !strategy.get().getActive()) {
            throw new IllegalArgumentException("Strategy not found");
        }

        strategy.get().setActive(false);
        Strategy savedStrategy = strategyRepository.save(strategy.get());

        return new StrategyDTO(savedStrategy);
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
