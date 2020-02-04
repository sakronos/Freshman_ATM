class Bank {
    //TODO 改写为Arrays
    private Account[] Accounts = new Account[100];
    private static double SumOfBalance = 0;
    private static double SumOfCeiling = 0;
    private static Bank instance = null;
    private static double SumOfLoan = 0;


    public static void setSumOfLoan(double sumOfLoan) {
        SumOfLoan = sumOfLoan;
    }

    private Bank() {

    }

    public static Bank getInstance() {
        if (instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    private static int quantity = 0;

    //密码，名字，身份证，邮箱，账户类型
    public Account register(int p, String n, String pe, String e, String type) {
        quantity++;

        if (type == "SavingAccount") {
            Accounts[(int) (Account.id_top - 100000)] = new SavingAccount(Account.id_top, p, n, pe, e);
            System.out.println("SavingAccount " + Accounts[(int) (Account.id_top - 100001)] + " is created.");
            return Accounts[(int) (Account.id_top - 100001)];
        } else if (type == "CreditAccount") {
            Accounts[(int) (Account.id_top - 100000)] = new CreditAccount(Account.id_top, p, n, pe, e);
            //SumOfCeiling += 1000;
            System.out.println("CreditAccount " + Accounts[(int) (Account.id_top - 100001)] + " is created.");
            return Accounts[(int) (Account.id_top - 100001)];
        } else if (type == "LoanSavingAccount") {
            Accounts[(int) (Account.id_top - 100000)] = new LoanSavingAccount(Account.id_top, p, n, pe, e);
            LoanSavingAccount LSA = (LoanSavingAccount) Accounts[(int) (Account.id_top - 100001)];
            System.out.println(LSA + " is created.");
            return Accounts[(int) (Account.id_top - 100001)];
        } else {
            Accounts[(int) (Account.id_top - 100000)] = new LoanCreditAccount(Account.id_top, p, n, pe, e);
            LoanCreditAccount LCA = (LoanCreditAccount) Accounts[(int) (Account.id_top - 100001)];
            System.out.println(LCA + " is created.");
            return Accounts[(int) (Account.id_top - 100001)];
        }

    }

    public Account login(long i, int p) {  //ID+密码
        if (Accounts[(int) i - 100000].getPassword() == p) {
            System.out.println(Accounts[(int) i - 100000] + " log in successfully.");
            System.out.println(Accounts[(int) i - 100000]+" 余额：" +Accounts[(int) i - 100000].getBalance());
            return Accounts[(int) i - 100000];
        }
        System.out.println("Failed, please try again.");
        return null;
    }

    public Account deposit(long id, double num) {
        Account t = Accounts[(int) id - 100000];
        t.setBalance(t.balance + num);
        System.out.println(t + " 余额： " + t.getBalance());
        SumOfBalance += num;
        return t;
    }

    public Account withdraw(long id, double num) {
        if (Accounts[(int) id - 100000].withdraw(num)) {
            SumOfBalance -= num;
            System.out.println(Accounts[(int) id - 100000] + " 余额： " + Accounts[(int) id - 100000].getBalance());
        }
        return Accounts[(int) id - 100000];
    }

    public Account setCeiling(long id, double num) {
        if (Accounts[(int) id - 100000] instanceof CreditAccount) {
            CreditAccount tr = (CreditAccount) Accounts[(int) id - 100000];
            SumOfCeiling -= tr.ceiling;
            SumOfCeiling += num;
            tr.ceiling = num;
            System.out.println(tr + " 透支额度：" + tr.getCeiling());
            return tr;
        }
        return null;
    }

    public boolean transfer(long from, long to, double money) {
        if (Accounts[(int) from - 100000] instanceof CreditAccount) return false;
        else {
            if (Accounts[(int) from - 100000].getBalance() < money) return false;
            else {
                /*
                withdraw(from ,money);
                deposit(to, money);
                 */
                SavingAccount fr = (SavingAccount) Accounts[(int) (from - 100000)];
                SavingAccount tu = (SavingAccount) Accounts[(int) (to - 100000)];
                fr.withdraw(money);
                Account.deposit(Accounts[(int) (to - 100000)], money);
                System.out.println(fr + " 向 " + tu + " 转账操作成功");
                return true;
            }
        }
    }

    public double getSumOfBalance() {
        return SumOfBalance;
    }

    public double getSumOfCeiling() {
        return SumOfCeiling;
    }

    public Account getAct(long id) {
        return Accounts[(int) (id - 100000)];
    }

    public Account requestLoan(long id, double money) {
        if (getAct(id) instanceof LoanSavingAccount) {
            ((LoanSavingAccount) getAct(id)).requestLoan(money);
            setSumOfLoan(money + getSumOfLoan());
            System.out.println(((LoanSavingAccount) getAct(id)) + " 成功贷款：" + money);
            return ((LoanSavingAccount) getAct(id));
        } else if (getAct(id) instanceof LoanCreditAccount) {
            ((LoanCreditAccount) getAct(id)).requestLoan(money);
            setSumOfLoan(money + getSumOfLoan());
            System.out.println(((LoanCreditAccount) getAct(id)) + " 成功贷款：" + money);
            return ((LoanCreditAccount) getAct(id));
        }
        return null;
    }

    public static double getSumOfLoan() { //统计所有账户贷款总数
        return SumOfLoan;
    }

    public Account payLoan(long id, double money) {
        if (getAct(id) instanceof LoanSavingAccount) {
            LoanSavingAccount LSA = (LoanSavingAccount) getAct(id);
            if (LSA.payLoan(money)) {
                setSumOfLoan(getSumOfLoan() - money);
                System.out.println(LSA + " 成功还款：" + money);
                return LSA;
            }
        } else if (getAct(id) instanceof LoanCreditAccount) {
            LoanCreditAccount LCA = (LoanCreditAccount) getAct(id);
            if (LCA.payLoan(money)) {
                setSumOfLoan(getSumOfLoan() - money);
                System.out.println(LCA + " 成功还款：" + money);
                return LCA;
            }
        }
        System.out.println("还贷操作失败");
        return null;
    }

}