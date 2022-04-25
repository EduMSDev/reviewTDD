package junit5.examples.models;

import junit5.examples.models.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {
    Account account;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Init the test!");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finish the test!");
    }

    @BeforeEach
    void initMethodTest() {
        account = new Account("Andres", new BigDecimal("1000.12345"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finished Tests!");
    }

    @Test
    //Allows to display a description of the name
    @DisplayName("Testing account name")
    //Allows you to disable a test
    @Disabled
    void AccountTestName() {
        account = new Account("Andres", new BigDecimal("1000.12345"));
        account.setPerson("Andres");
        String nameToTest = "Andres";
        String real = account.getPerson();
        //A lambda message is passed that is only created if it fails
        assertEquals(real, nameToTest, () -> "Not can be null");
    }

    @Test
    void balanceAccountTest() {
        account = new Account("Andres", new BigDecimal("1000.12345"));
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
        account = new Account("Andres", new BigDecimal("1000.12345"));
        account.debit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12345", account.getBalance().toPlainString());

    }

    @Test
    void creditAccountTest() {
        account = new Account("Andres", new BigDecimal("1000.12345"));
        account.credit(new BigDecimal("100"));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.12345", account.getBalance().toPlainString());
    }

    @Test
    void NotEnoughMoneyExceptionTest() {
        account = new Account("Andres", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(NotEnoughMoneyException.class, () -> {
            account.debit(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String expected = "Not enough money!";
        assertEquals(actual, expected);
    }

    @Test
    void transferAccountTest() {
        Account account1 = new Account("John Doe", new BigDecimal("2500"));
        Account account2 = new Account("Andres", new BigDecimal("1500"));
        Bank bank = new Bank();
        bank.setName("Santander");
        bank.transfer(account1, account2, new BigDecimal(500));
        assertEquals("3000", account1.getBalance().toPlainString());
        assertEquals("1000", account2.getBalance().toPlainString());
    }

    @Test
    void relationAccountBankTest() {
        Account account1 = new Account("John Doe", new BigDecimal("2500"));
        Account account2 = new Account("Andres", new BigDecimal("1500"));
        Bank bank = new Bank();
        bank.setName("Santander");
        bank.addAccount(account1);
        bank.addAccount(account2);

        assertAll(() -> assertEquals(2, bank.getAccounts().size()),
                () -> assertEquals("Santander", account1.getBank().getName()),
                () -> assertEquals("Andres", bank.getAccounts().stream().filter(c -> c.getPerson().equals("Andres")).findFirst().get().getPerson()),
                () -> assertTrue(bank.getAccounts().stream().anyMatch(c -> c.getPerson().equals("Andres"))));
    }
}