package ch.frankel.springboot.bank;

import org.apache.commons.validator.routines.IBANValidator;

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
}
