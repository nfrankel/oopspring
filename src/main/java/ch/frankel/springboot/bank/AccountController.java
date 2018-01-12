package ch.frankel.springboot.bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private final AccountService service;
    private final AccountRepository repository;

    public AccountController(AccountService service, AccountRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/account/{iban}")
    public Account read(@PathVariable("iban") String iban) {
        return new Iban(iban).get(repository);
    }

    @GetMapping("/account")
    public List<Account> read() {
        return service.getAll();
    }

    @PutMapping("/account/{fromIban}/transfer/{toIban}/{amount}")
    public List<Account> transfer(@PathVariable("fromIban") String fromIban, @PathVariable("toIban") String toIban, @PathVariable("amount") BigDecimal amount) {
        return new Iban(fromIban).transfer(new Iban(toIban), amount, repository);
    }
}
