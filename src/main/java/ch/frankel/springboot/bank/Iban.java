package ch.frankel.springboot.bank;

public class Iban {

    public final String number;

    public Iban(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Iban[" + number + ']';
    }
}
