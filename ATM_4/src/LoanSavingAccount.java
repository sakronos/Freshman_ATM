public class LoanSavingAccount extends SavingAccount implements Loanable {
    LoanSavingAccount(long i, int p, String n, String pe, String e) {
        super(i, p, n, pe, e);
    }

    @Override
    public boolean requestLoan(double money) {
        loan += money;
        return true;
    }

    @Override
    public boolean payLoan(double money) {
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
        return "LoanSavingAccount id:" + id;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
