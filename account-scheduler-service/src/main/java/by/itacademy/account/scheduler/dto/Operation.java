package by.itacademy.account.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.UUID;

@JsonPropertyOrder({"account", "description", "value", "currency", "category"})
public class Operation {

    @JsonProperty("account")
    private UUID account;
    private String description;
    private BigDecimal value;
    private UUID currency;
    private UUID category;

    public Operation() {
    }

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "account=" + account +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", currency=" + currency +
                ", category=" + category +
                '}';
    }
}
