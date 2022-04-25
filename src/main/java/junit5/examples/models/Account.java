package junit5.examples.models;

import java.math.BigDecimal;

public class Account {

    private String person;
    private BigDecimal balance;
    public Account(String person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
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
