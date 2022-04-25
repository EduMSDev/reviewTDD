package junit5.examples.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final List<Account> accounts;
    private String name;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setBank(this);

    }

    public void transfer(Account destination, Account origin, BigDecimal amount) {
        origin.debit(amount);
        destination.credit(amount);
    }
}
