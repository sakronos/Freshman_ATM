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

    public boolean withdraw(double num) {
        if (num <= balance + ceiling - used_quota) {
            if (num <= balance) {
                setBalance(balance - num);
                return true;
            } else {
                setBalance(0);
                setUsed_quota(num - balance);
                return true;
            }
        } else {
            System.out.println("Failed.");
            return false;
        }
    }

    public static Account repay(CreditAccount a, double num) { // 还款方法
        if (num <= a.used_quota) {
            a.used_quota -= num;
        } else {
            a.setUsed_quota(0);
            deposit(a, num - a.used_quota);

        }
        return a;

    }
}