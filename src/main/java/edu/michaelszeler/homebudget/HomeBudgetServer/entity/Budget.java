package edu.michaelszeler.homebudget.HomeBudgetServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "budget")
@Data
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "budget", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<StrategyInstallment> strategyInstallments;

    @OneToMany(mappedBy = "budget", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<RegularExpenseInstallment> regularExpenseInstallments;

    /*@OneToMany(targetEntity = CustomExpense.class, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "budget_id")*/
    @OneToMany(mappedBy = "budget", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<CustomExpense> customExpenseList;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    public Budget() {
    }

    public Budget(Integer id, User user, List<StrategyInstallment> strategyInstallments, List<RegularExpenseInstallment> regularExpenseInstallments, List<CustomExpense> customExpenseList, LocalDate date, BigDecimal income) {
        this.id = id;
        this.user = user;
        this.strategyInstallments = strategyInstallments;
        this.regularExpenseInstallments = regularExpenseInstallments;
        this.customExpenseList = customExpenseList;
        this.date = date;
        this.income = income;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StrategyInstallment> getStrategyInstallments() {
        return strategyInstallments;
    }

    public void setStrategyInstallments(List<StrategyInstallment> strategyInstallments) {
        this.strategyInstallments = strategyInstallments;
    }

    public List<RegularExpenseInstallment> getRegularExpenseInstallments() {
        return regularExpenseInstallments;
    }

    public void setRegularExpenseInstallments(List<RegularExpenseInstallment> regularExpenseInstallments) {
        this.regularExpenseInstallments = regularExpenseInstallments;
    }

    public List<CustomExpense> getCustomExpenseList() {
        return customExpenseList;
    }

    public void setCustomExpenseList(List<CustomExpense> customExpenseList) {
        this.customExpenseList = customExpenseList;
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

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", income=" + income +
                '}';
    }
}
