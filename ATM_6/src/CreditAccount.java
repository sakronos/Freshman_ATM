import BAMSException.BalanceNotEnoughException;

import java.util.Objects;

class CreditAccount extends Account {
    protected double ceiling = 0;
    private double used_quota = 0;

    CreditAccount(long i, int p, String n, String pe, String e) {
        super(i, p, n, pe, e);
    }

    public double getCeiling() {
        return ceiling;
    }

    public CreditAccount setCeiling(CreditAccount a, double m) {
        ceiling = m;
        return a;
    }

    public double getUsed_quota() {
        return used_quota;
    }

    private void setUsed_quota(double used_quota) {
        this.used_quota = used_quota;
    }

    public boolean withdraw(double num) throws BalanceNotEnoughException {
        if (num <= balance + ceiling - used_quota) {
            if (num <= balance) {
                setBalance(balance - num);
                return true;
            } else {
                setBalance(0);
                setUsed_quota(getUsed_quota() + num - balance);
                return true;
            }
        } else {
            throw new BalanceNotEnoughException("账户余额超过透支额");
            //System.out.println("Failed.");
            //return false;
        }
    }

    public Account repay(CreditAccount a, double num) { // 透支还款方法
        if (num <= a.used_quota) {
            a.used_quota -= num;
        } else {
            a.setUsed_quota(0);
            deposit(a, num - a.used_quota);

        }
        return a;

    }

    @Override
    public String toString() {
        return "CreditAccount id:" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            CreditAccount that = (CreditAccount) o;
            return Double.compare(that.ceiling, ceiling) == 0 &&
                    Double.compare(that.used_quota, used_quota) == 0;
        } else
            return false;
    }


}