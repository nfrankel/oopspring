package ch.frankel.springboot.bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/account/{iban}")
    public Account read(@PathVariable("iban") Iban iban) {
        return iban.get();
    }

    @GetMapping("/account")
    public List<Account> read() {
        return repository.findAll();
    }

    @PutMapping("/account/{fromIban}/transfer/{toIban}/{amount}")
    public List<Account> transfer(@PathVariable("fromIban") Iban fromIban, @PathVariable("toIban") Iban toIban, @PathVariable("amount") BigDecimal amount) {
        return fromIban.transfer(toIban, amount);
    }
}