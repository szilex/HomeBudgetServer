package edu.michaelszeler.homebudget.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.michaelszeler.homebudget.server.entity.RegularExpense;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegularExpenseDTO {

    private Integer id;
    private String name;
    private String category;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    private Integer months;
    private Boolean active;

    public RegularExpenseDTO() {
    }

    public RegularExpenseDTO(Integer id, String name, String category, BigDecimal amount, LocalDate startDate, Integer months) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.months = months;
    }

    public RegularExpenseDTO(RegularExpense expense) {
        this.id = expense.getId();
        this.category = expense.getCategory().getName();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.startDate = expense.getStartDate();
        this.months = expense.getMonths();
        this.active = expense.getActive();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean allSetForPost() {
        return name != null && category != null && amount != null && startDate != null && months != null;
    }
}
