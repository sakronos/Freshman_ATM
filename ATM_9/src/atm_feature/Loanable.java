package atm_feature;
import BAMSException.BalanceNotEnoughException;
import BAMSException.LoanException;

interface Loanable {
    public boolean requestLoan(double money) throws LoanException;

    public boolean payLoan(double money) throws BalanceNotEnoughException, LoanException;

    public double getLoan();
}
