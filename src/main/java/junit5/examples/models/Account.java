package junit5.examples.models;

import junit5.examples.models.exceptions.NotEnoughMoneyException;

import java.math.BigDecimal;

public class Account {

    private Bank bank;
    private String person;
    private BigDecimal balance;

    public Account(String person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void debit(BigDecimal amount) {
        BigDecimal newBalance = this.balance.subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException("Not enough money!");
        }

        this.balance = newBalance;
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Account)) {
            return false;
        }

        Account account = (Account) obj;

        if (this.person == null || this.balance == null) {
            return false;
        }
        return this.person.equals(account.getPerson()) && this.balance.equals(account.getBalance());
    }
}
