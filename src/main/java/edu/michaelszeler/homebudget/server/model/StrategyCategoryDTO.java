package edu.michaelszeler.homebudget.server.model;

import edu.michaelszeler.homebudget.server.entity.StrategyCategory;

public class StrategyCategoryDTO {

    private String name;
    private String description;

    public StrategyCategoryDTO() {
    }

    public StrategyCategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public StrategyCategoryDTO(StrategyCategory category) {
        this.name = category.getName();
        this.description = category.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
