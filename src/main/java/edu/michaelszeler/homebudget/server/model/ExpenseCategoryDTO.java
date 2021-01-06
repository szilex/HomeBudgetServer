package edu.michaelszeler.homebudget.server.model;

import edu.michaelszeler.homebudget.server.entity.ExpenseCategory;

public class ExpenseCategoryDTO {

    private String name;
    private String description;

    public ExpenseCategoryDTO() {
    }

    public ExpenseCategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ExpenseCategoryDTO(ExpenseCategory category) {
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
