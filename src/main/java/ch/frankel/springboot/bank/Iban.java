package ch.frankel.springboot.bank;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Iban {

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

    public Account get(AccountRepository repository) {
        return repository.findByIban(number).orElseThrow(() -> new NoSuchElementException("No account found with IBAN " + this));
    }

    @Transactional
    public List<Account> transfer(Iban toIban, BigDecimal amount, AccountRepository repository) {
        Account from = get(repository);
        Account to = toIban.get(repository);
        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));
        repository.save(from);
        repository.save(to);
        repository.flush();
        return Arrays.asList(from, to);
    }
}
