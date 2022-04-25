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

    @Test
    void balanceAccountTest() {
        Account account = new Account("Andres", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, account.getBalance().doubleValue());
        assertFalse(account.getBalance().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
        //TDD
    void referenceMethodTest() {
        Account account = new Account("John", new BigDecimal("8900.9997"));
        Account account2 = new Account("John", new BigDecimal("8900.9992"));
        assertNotEquals(account, account2);
    }

    @Test
    void debitAccountTest() {
        Account account = new Account("Andres", new BigDecimal("1000.12345"));
        account.debit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12345", account.getBalance().toPlainString());

    }

    @Test
    void creditAccountTest() {
        Account account = new Account("Andres", new BigDecimal("1000.12345"));
        account.credit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.12345", account.getBalance().toPlainString());

    }
}