package edu.michaelszeler.homebudget.server.entity;

import java.io.Serializable;
import java.util.Objects;

public class RegularExpenseInstallmentId implements Serializable {

    private RegularExpense regularExpense;
    private Budget budget;

    public RegularExpenseInstallmentId() {
    }

    public RegularExpenseInstallmentId(RegularExpense regularExpense, Budget budget) {
        this.regularExpense = regularExpense;
        this.budget = budget;
    }

    public RegularExpense getRegularExpense() {
        return regularExpense;
    }

    public void setRegularExpense(RegularExpense regularExpense) {
        this.regularExpense = regularExpense;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularExpenseInstallmentId that = (RegularExpenseInstallmentId) o;
        return regularExpense.equals(that.regularExpense) &&
                budget.equals(that.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regularExpense, budget);
    }
}
