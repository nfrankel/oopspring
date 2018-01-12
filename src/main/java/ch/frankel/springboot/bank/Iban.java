package ch.frankel.springboot.bank;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Configurable(autowire = Autowire.BY_TYPE)
public class Iban {

    @Transient
    @Autowired
    private AccountRepository repository;

    public final String number;

    public Iban(String number) {
        validate(number);
        this.number = number;
    }

    @Override
    public String toString() {
        return "Iban[" + number + ']';
    }

    private void validate(String number) {
        if (!IBANValidator.getInstance().isValid(number)) {
            throw new IllegalArgumentException("Wrong format for IBAN " + number);
        }
    }

    public Account get() {
        return repository.findByIban(number).orElseThrow(() -> new NoSuchElementException("No account found with IBAN " + this));
    }

    @Transactional
    public List<Account> transfer(Iban toIban, BigDecimal amount) {
        Account from = get();
        Account to = toIban.get();
        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));
        repository.save(from);
        repository.save(to);
        repository.flush();
        return Arrays.asList(from, to);
    }
}
