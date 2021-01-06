package edu.michaelszeler.homebudget.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.michaelszeler.homebudget.server.entity.CustomExpense;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomExpenseDTO {

    private Integer id;
    private String category;
    private String name;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private Integer months;

    public CustomExpenseDTO() {
    }

    public CustomExpenseDTO(String category, String name, BigDecimal amount, LocalDate date, Integer months) {
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.months = months;
    }

    public CustomExpenseDTO(Integer id, String category, String name, BigDecimal amount, LocalDate date, Integer months) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.months = months;
    }

   public CustomExpenseDTO(CustomExpense expense) {
        this.id = expense.getId();
        this.category = expense.getCategory().getName();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.date = expense.getDate();
        this.months = expense.getMonths();
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public boolean allSetForPost() {
        return name != null && category != null && amount != null && date != null && months != null;
    }
}
