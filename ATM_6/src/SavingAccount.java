import BAMSException.BalanceNotEnoughException;

class SavingAccount extends Account {
    SavingAccount(long i, int p, String n, String pe, String e) {
        super(i, p, n, pe, e);
    }

    public boolean withdraw(double num) throws BalanceNotEnoughException {
        if (num <= balance) {
            setBalance(balance - num);
            return true;
        } else {
            throw new BalanceNotEnoughException("余额不足");
            //System.out.println("Failed.");
            //return false;
        }
    }

    @Override
    public String toString() {
        return "SavingAccount id:" + id;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}