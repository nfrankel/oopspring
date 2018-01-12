package ch.frankel.springboot.bank;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account get(String iban) {
        validate(iban);
        return repository.findByIban(iban).orElseThrow(() -> new NoSuchElementException("No account found with IBAN " + iban));
    }

    private void validate(String iban) {
        if (!IBANValidator.getInstance().isValid(iban)) {
            throw new IllegalArgumentException("Wrong format for IBAN " + iban);
        }
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    @Transactional
    public List<Account> transfer(String fromIban, String toIban, BigDecimal amount) {
        Account from = get(fromIban);
        Account to = get(toIban);
        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));
        repository.save(from);
        repository.save(to);
        repository.flush();
        return Arrays.asList(from, to);
    }
}
