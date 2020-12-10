package edu.michaelszeler.homebudget.HomeBudgetServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "regular_expense_installment")
@Entity
@Data
//@IdClass(RegularExpenseInstallmentId.class)
public class RegularExpenseInstallment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //@Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    //@Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "regular_expense_id")
    private RegularExpense regularExpense;

    public RegularExpenseInstallment() {
    }

    public RegularExpenseInstallment(Integer id, Budget budget, RegularExpense regularExpense) {
        this.id = id;
        this.budget = budget;
        this.regularExpense = regularExpense;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public RegularExpense getRegularExpense() {
        return regularExpense;
    }

    public void setRegularExpense(RegularExpense regularExpense) {
        this.regularExpense = regularExpense;
    }
}
