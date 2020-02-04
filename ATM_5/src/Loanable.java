interface Loanable {
    public boolean requestLoan(double money);

    public boolean payLoan(double money);

    public double getLoan();
}
