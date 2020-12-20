package edu.michaelszeler.homebudget.HomeBudgetServer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrategyDTO {

    private Integer id;
    private String name;
    private String description;
    private String category;
    private BigDecimal goal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    private Integer months;
    private Boolean active;

    public StrategyDTO() {
    }

    public StrategyDTO(Integer id, String name, String description, String category, BigDecimal goal, LocalDate startDate, Integer months, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.goal = goal;
        this.startDate = startDate;
        this.months = months;
        this.active = active;
    }

    public StrategyDTO(Strategy strategy) {
        this.id = strategy.getId();
        this.name = strategy.getName();
        this.description = strategy.getDescription();
        this.category = strategy.getCategory().getName();
        this.goal = strategy.getGoal();
        this.startDate = strategy.getStartDate();
        this.months = strategy.getMonths();
        this.active = strategy.getActive();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean allSetForPost() {
        return name != null && goal != null && startDate != null && months != null;
    }
}
