package ch.frankel.springboot.bank;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IbanConverter implements Converter<String, Iban> {

    @Override
    public Iban convert(String s) {
        return new Iban(s);
    }
}
