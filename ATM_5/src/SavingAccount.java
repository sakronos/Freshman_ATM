class SavingAccount extends Account {
    SavingAccount(long i, int p, String n, String pe, String e) {
        super(i, p, n, pe, e);
    }

    public boolean withdraw(double num) {
        if (num <= balance) {
            setBalance(balance - num);
            return true;
        } else {
            System.out.println("Failed.");
            return false;
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