package edu.michaelszeler.homebudget.HomeBudgetServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "regular_expense")
@Data
public class RegularExpense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    /*@OneToMany(mappedBy = "regularExpense", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<RegularExpenseInstallment> regularExpenseInstallments;*/

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "months", nullable = false)
    private Integer months;

    public RegularExpense() {
    }

    public RegularExpense(Integer id, User user, ExpenseCategory category, /*List<RegularExpenseInstallment> regularExpenseInstallments.*/ String name, BigDecimal amount, LocalDate startDate, Integer months) {
        this.id = id;
        this.user = user;
        this.category = category;
        //this.regularExpenseInstallments = regularExpenseInstallments;
        this.name = name;
        this.amount = amount;
        this.startDate = startDate;
        this.months = months;
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

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    /*public List<RegularExpenseInstallment> getRegularExpenseInstallments() {
        return regularExpenseInstallments;
    }

    public void setRegularExpenseInstallments(List<RegularExpenseInstallment> regularExpenseInstallments) {
        this.regularExpenseInstallments = regularExpenseInstallments;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }
}
