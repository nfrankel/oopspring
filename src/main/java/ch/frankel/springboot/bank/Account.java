package ch.frankel.springboot.bank;

import com.fasterxml.jackson.core.JsonGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    private Iban iban;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void serialize(JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeNumberField("id", id);
        generator.writeNumberField("amount", amount);
        generator.writeStringField("iban", iban.getNumber());
        generator.writeEndObject();
    }
}