import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class Bank {
    private List<Account> Accounts = new ArrayList<Account>();
    //private Account[] Accounts = new Account[100];
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
            Accounts.add(new SavingAccount(Account.id_top, p, n, pe, e));
            System.out.println(Accounts.get(getActIn(Account.id_top - 1)) + " is created.");
            return Accounts.get(getActIn(Account.id_top - 1));
        } else if (type == "CreditAccount") {
            Accounts.add(new CreditAccount(Account.id_top, p, n, pe, e));
            System.out.println(Accounts.get(getActIn(Account.id_top - 1)) + " is created.");
            return Accounts.get(getActIn(Account.id_top - 1));
        } else if (type == "LoanSavingAccount") {
            Accounts.add(new LoanSavingAccount(Account.id_top, p, n, pe, e));//[(int) (Account.id_top - 100000)] = ;
            LoanSavingAccount LSA = (LoanSavingAccount) Accounts.get(getActIn(Account.id_top - 1));
            System.out.println(LSA + " is created.");
            return Accounts.get(getActIn(Account.id_top - 1));
        } else {
            Accounts.add(new LoanCreditAccount(Account.id_top, p, n, pe, e));//[(int) (Account.id_top - 100000)] = ;
            LoanCreditAccount LCA = (LoanCreditAccount) Accounts.get(getActIn(Account.id_top - 1));
            System.out.println(LCA + " is created.");
            return Accounts.get(getActIn(Account.id_top - 1));
        }

    }

    public Account login(long i, int p) {  //ID+密码
        if (Accounts.get(getActIn(i)).getPassword() == p) {
            System.out.println(getAct(i) + " log in successfully.");
            System.out.println(getAct(i) + " 余额：" + getAct(i).getBalance());
            return getAct(i);
        }
        System.out.println("Failed, please try again.");
        return null;
    }

    public Account deposit(long id, double num) {
        Account t = getAct(id);
        t.setBalance(t.balance + num);
        System.out.println(t + " 成功存入： " + num);
        System.out.println(t + " 余额： " + t.getBalance());
        SumOfBalance += num;
        return t;
    }

    public Account withdraw(long id, double num) {
        if (getAct(id).withdraw(num)) {
            SumOfBalance -= num;
            System.out.println(getAct(id) + " 余额： " + getAct(id).getBalance());
        }
        return getAct(id);
    }

    public Account setCeiling(long id, double num) {
        if (getAct(id) instanceof CreditAccount) {
            CreditAccount tr = (CreditAccount) getAct(id);
            SumOfCeiling -= tr.ceiling;
            SumOfCeiling += num;
            tr.ceiling = num;
            System.out.println(tr + " 透支额度：" + tr.getCeiling());
            return tr;
        }
        return null;
    }

    public boolean transfer(long from, long to, double money) {
        if (getAct(from) instanceof CreditAccount) return false;
        else {
            if (getAct(from).getBalance() < money) return false;
            else {
                /*
                withdraw(from ,money);
                deposit(to, money);
                 */
                SavingAccount fr = (SavingAccount) getAct(from);
                SavingAccount tu = (SavingAccount) getAct(to);
                fr.withdraw(money);
                Account.deposit(getAct(to), money);
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
        if (Accounts.get(getActIn(id)) instanceof Object)
            return Accounts.get(getActIn(id));
        else {
            throw new NullPointerException();
        }
    }

    public int getActIn(long id) {
        return (int) id - 100000;
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
                System.out.println(LSA + " 剩余待还：" + LSA.getLoan());
                return LSA;
            }
        } else if (getAct(id) instanceof LoanCreditAccount) {
            LoanCreditAccount LCA = (LoanCreditAccount) getAct(id);
            if (LCA.payLoan(money)) {
                setSumOfLoan(getSumOfLoan() - money);
                System.out.println(LCA + " 成功还款：" + money);
                System.out.println(LCA + " 剩余待还：" + LCA.getLoan());
                return LCA;
            }
        }
        System.out.println("还贷操作失败");
        return null;
    }

    private ArrayList<Account> UniAccount = new ArrayList<Account>();

    public void rankProperty() {
        System.out.println("总资产排名：");
        Iterator itA = Accounts.iterator();
        label:
        while (itA.hasNext()) {
            Account obj = (Account) itA.next();
            Iterator itU = UniAccount.iterator();
            while (itU.hasNext()) {
                Account temp = (Account) itU.next();
                if (temp.Eq(obj)) {
                    temp.setBalance(temp.getBalance() + obj.getBalance());
                    continue label;
                }
            }
            UniAccount.add(obj);
        }
        UniAccount.sort(new SortByBalance());
        Iterator it = UniAccount.iterator();
        int i = 1;
        while (it.hasNext()) {
            Account t = (Account) it.next();
            System.out.println(i++ + " " + t.getName() + "    " + t.getBalance());
        }
    }

    class SortByBalance implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Account fir = (Account) o1;
            Account sec = (Account) o2;
            return fir.getBalance() < sec.getBalance() ? 1 : -1;
        }
    }
}