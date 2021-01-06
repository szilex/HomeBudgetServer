package edu.michaelszeler.homebudget.server.entity;

import java.io.Serializable;
import java.util.Objects;

public class StrategyInstallmentId implements Serializable {

    private Strategy strategy;
    private Budget budget;

    public StrategyInstallmentId() {
    }

    public StrategyInstallmentId(Strategy strategy, Budget budget) {
        this.strategy = strategy;
        this.budget = budget;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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
        StrategyInstallmentId that = (StrategyInstallmentId) o;
        return strategy.equals(that.strategy) &&
                budget.equals(that.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strategy, budget);
    }
}
