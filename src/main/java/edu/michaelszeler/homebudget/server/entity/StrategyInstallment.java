package edu.michaelszeler.homebudget.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "strategy_installment")
@Entity
@Data
//@IdClass(StrategyInstallmentId.class)
public class StrategyInstallment implements Serializable {

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
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public StrategyInstallment() {
    }

    public StrategyInstallment(Integer id, Budget budget, Strategy strategy, BigDecimal amount) {
        this.id = id;
        this.budget = budget;
        this.strategy = strategy;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
