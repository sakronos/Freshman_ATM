class Bank {
    //TODO 改写为Arrays
    private Account[] Accounts = new Account[100];
    private double SumOfBalance = 0;
    private double SumOfCeiling = 0;
    private static Bank instance=null;

    private Bank() {

    }
    public static Bank getInstance() {
        if (instance==null) {
            instance =new Bank();
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
        } else {
            Accounts[(int) (Account.id_top - 100000)] = new CreditAccount(Account.id_top, p, n, pe, e);
            //SumOfCeiling += 1000;
            System.out.println("CreditAccount " + Accounts[(int) (Account.id_top - 100001)] + " is created.");
            return Accounts[(int) (Account.id_top - 100001)];
        }

    }

    public Account login(long i, int p) {  //ID+密码
        if (Accounts[(int) i - 100000].getPassword() == p) {
            System.out.println(Accounts[(int) i - 100000] + " log in successfully.");
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
                SavingAccount fr=(SavingAccount) Accounts[(int) (from - 100000)];
                SavingAccount tu=(SavingAccount) Accounts[(int) (to - 100000)];
                fr.withdraw(money);
                Account.deposit(Accounts[(int) (to - 100000)],money);
                System.out.println(fr+" 向 "+tu+" 转账操作成功");
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
}