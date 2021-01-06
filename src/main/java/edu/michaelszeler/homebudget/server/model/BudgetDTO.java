package edu.michaelszeler.homebudget.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.michaelszeler.homebudget.server.entity.Budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetDTO {

    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private BigDecimal income;
    private List<CustomExpenseDTO> customExpenses;
    private List<RegularExpenseDTO> regularExpenses;
    private List<StrategyDTO> strategies;

    public BudgetDTO() {
    }

    public BudgetDTO(List<CustomExpenseDTO> customExpenses, List<RegularExpenseDTO> regularExpenses, List<StrategyDTO> strategies, LocalDate date, BigDecimal income) {
        this.customExpenses = customExpenses;
        this.regularExpenses = regularExpenses;
        this.strategies = strategies;
        this.date = date;
        this.income = income;
    }

    public BudgetDTO(Integer id, LocalDate date, BigDecimal income, List<CustomExpenseDTO> customExpenses, List<RegularExpenseDTO> regularExpenses, List<StrategyDTO> strategies) {
        this.id = id;
        this.date = date;
        this.income = income;
        this.customExpenses = customExpenses;
        this.regularExpenses = regularExpenses;
        this.strategies = strategies;
    }

    public BudgetDTO(Budget budget) {
        this.id = budget.getId();
        this.customExpenses = budget.getCustomExpenseList().stream()
                .map(CustomExpenseDTO::new)
                .collect(Collectors.toList());
        this.regularExpenses = budget.getRegularExpenseInstallments().stream()
                .map(installment -> new RegularExpenseDTO(installment.getRegularExpense()))
                .collect(Collectors.toList());
        this.strategies = budget.getStrategyInstallments().stream()
                .map(installment -> new StrategyDTO(installment.getStrategy()))
                .collect(Collectors.toList());
        this.date = budget.getDate();
        this.income = budget.getIncome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public List<CustomExpenseDTO> getCustomExpenses() {
        return customExpenses;
    }

    public void setCustomExpenses(List<CustomExpenseDTO> customExpenses) {
        this.customExpenses = customExpenses;
    }

    public List<RegularExpenseDTO> getRegularExpenses() {
        return regularExpenses;
    }

    public void setRegularExpenses(List<RegularExpenseDTO> regularExpenses) {
        this.regularExpenses = regularExpenses;
    }

    public List<StrategyDTO> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<StrategyDTO> strategies) {
        this.strategies = strategies;
    }

    public boolean allSetForPost() {
        return date != null && income != null;
    }
}
