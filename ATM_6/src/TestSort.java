import BAMSException.LoginException;
import BAMSException.RegisterException;

public class TestSort {
    public static void main(String[] args) throws RegisterException, LoginException {
        Bank.getInstance().register(123456, 123456, "Li Zheyuan", "000",
                "lzy@a.io", "LoanSavingAccount");    //0
        Bank.getInstance().register(123456, 123456, "Li Zheyuan", "000",
                "lzy@a.io", "SavingAccount");    //1
        Bank.getInstance().register(123456, 123456, "Li Zheyuan", "000",
                "lzy@a.io", "CreditAccount");    //2
        Bank.getInstance().register(123456, 123456, "Alice", "001",
                "lzy@a.io", "LoanSavingAccount");    //3
        Bank.getInstance().register(123456, 123456, "Justin", "002",
                "lzy@a.io", "SavingAccount");    //4
        Bank.getInstance().register(123456, 123456, "Lance", "003",
                "lzy@a.io", "LoanCreditAccount");    //5
        Bank.getInstance().register(123456, 123456, "Light", "005",
                "lzy@a.io", "LoanSavingAccount");    //6
        Bank.getInstance().register(123456, 123456, "Light", "005",
                "lzy@a.io", "SavingAccount");    //7
        Bank.getInstance().deposit(100000, 500);
        Bank.getInstance().deposit(100001, 200);
        Bank.getInstance().deposit(100002, 50);
        Bank.getInstance().deposit(100003, 300);
        Bank.getInstance().deposit(100004, 3000);
        Bank.getInstance().deposit(100005, 5000);
        Bank.getInstance().deposit(100006, 4000);
        Bank.getInstance().deposit(100007, 500);
        Bank.getInstance().rankProperty();
    }
}
