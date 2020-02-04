public class Task5 {
    public static void main(String[] args) {
        Bank.getInstance().register(123456, "Li Zheyuan", "000",
                "lzy@a.io", "LoanSavingAccount");
        Bank.getInstance().requestLoan(100000, 10000);
        Bank.getInstance().deposit(100000, 500000);
        Bank.getInstance().payLoan(100000, 500);
        Bank.getInstance().login(100000, 123456);
        Bank.getInstance().register(123456, "Lance", "001",
                "lzy@a.com", "LoanCreditAccount");
        Bank.getInstance().requestLoan(100001, 10000);
        Bank.getInstance().deposit(100001, 400);
        Bank.getInstance().payLoan(100001, 500);
        Bank.getInstance().login(100001, 123456);
        Bank.getInstance().register(123123, "Alice", "1354612", "123@456.cn", "SavingAccount");
    }
}