import BAMSException.BalanceNotEnoughException;
import BAMSException.LoanException;

public class LoanCreditAccount extends CreditAccount implements Loanable {
    LoanCreditAccount(long i, int p, String n, String pe, String e) {
        super(i, p, n, pe, e);
    }

    @Override
    public boolean requestLoan(double money) throws LoanException {
        if (money < 0) {
            throw new LoanException();
        }
        loan += money;
        return true;
    }

    @Override
    public boolean payLoan(double money) throws BalanceNotEnoughException {
        if (!withdraw(money)) {
            return false;
        }
        loan -= money;
        return true;
    }

    @Override
    public double getLoan() {
        return loan;
    }

    @Override
    public String toString() {
        return "LoanCreditAccount id:" + id;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
