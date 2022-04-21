package junit5.examples.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void AccountTestName() {
        Account account = new Account("Andres", new BigDecimal("1000.12345"));
        account.setPerson("Andres");
        String nameToTest = "Andres";
        String real = account.getPerson();
        assertEquals(real, nameToTest);
    }

}