package atm_feature;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import BAMSException.*;

public class Bank {
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
    public Account register(int p, int pa, String n, String pe,
                            String e, String type) throws RegisterException, IOException {
        for (Account t:Accounts) {
            if (t.getId_top()==t.getId()) throw new RegisterException("ID已存在");
        }
        if (pa != p) throw new RegisterException("两次输入密码不一致");
        quantity++;

        if (type == "SavingAccount") {
            Accounts.add(new SavingAccount(0, p, n, pe, e));
            System.out.println(Accounts.get(Accounts.size()-1) + " is created.");
            Serialize();
            return Accounts.get(Accounts.size()-1);
        } else if (type == "CreditAccount") {
            Accounts.add(new CreditAccount(0, p, n, pe, e));
            System.out.println(Accounts.get(Accounts.size()-1) + " is created.");
            Serialize();
            return Accounts.get(Accounts.size()-1);
        } else if (type == "LoanSavingAccount") {
            Accounts.add(new LoanSavingAccount(0, p, n, pe, e));//[(int) (Account.id_top - 100000)] = ;
            LoanSavingAccount LSA = (LoanSavingAccount) Accounts.get(Accounts.size()-1);
            System.out.println(LSA + " is created.");
            Serialize();
            return Accounts.get(Accounts.size()-1);
        } else if (type == "LoanCreditAccount") {
            Accounts.add(new LoanCreditAccount(0, p, n, pe, e));//[(int) (Account.id_top - 100000)] = ;
            LoanCreditAccount LCA = (LoanCreditAccount) Accounts.get(Accounts.size()-1);
            System.out.println(LCA + " is created.");
            Serialize();
            return Accounts.get(Accounts.size()-1);
        } else throw new RegisterException("账户类型错误");

    }

    public Account login(long i, int p) throws LoginException {  //ID+密码
        if (getAct(i).getPassword() == p) {
            System.out.println(getAct(i) + " log in successfully.");
            System.out.println(getAct(i) + " 余额：" + getAct(i).getBalance());
            return getAct(i);
        } else throw new LoginException("密码错误");
        //System.out.println("Failed, please try again.");
        //return null;
    }

    public Account deposit(long id, double num) throws LoginException, IOException {
        Account t = getAct(id);
        t.setBalance(t.balance + num);
        System.out.println(t + " 成功存入： " + num);
        System.out.println(t + " 余额： " + t.getBalance());
        SumOfBalance += num;
        Serialize();
        return t;
    }

    public Account withdraw(long id, double num) throws LoginException, BalanceNotEnoughException, IOException {
        if (getAct(id).withdraw(num)) {
            SumOfBalance -= num;
            System.out.println(getAct(id) + " 余额： " + getAct(id).getBalance());
        }
        Serialize();
        return getAct(id);
    }

    public Account setCeiling(long id, double num) throws ATMException, IOException {
        if (getAct(id) instanceof CreditAccount) {
            CreditAccount tr = (CreditAccount) getAct(id);
            SumOfCeiling -= tr.ceiling;
            SumOfCeiling += num;
            tr.ceiling = num;
            System.out.println(tr + " 透支额度：" + tr.getCeiling());
            Serialize();
            return tr;
        }
        throw new ATMException("账户类型错误");
    }

    public boolean transfer(long from, long to, double money) throws ATMException, IOException {
        if (getAct(from) instanceof CreditAccount) throw new ATMException("账户不支持该功能");
        else {
            if (getAct(from).getBalance() < money) return false;
            else {
                SavingAccount fr = (SavingAccount) getAct(from);
                Account tu = (Account) getAct(to);
                fr.withdraw(money);
                Account.deposit(getAct(to), money);
                System.out.println(fr + " 向 " + tu + " 转账操作成功");
                Serialize();
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

    public Account getAct(long id) throws LoginException {
        Iterator iA=Accounts.iterator();
        while (iA.hasNext()) {
            Account obj=(Account)iA.next();
            if (obj.id==id) return obj;
        }
            //throw new NullPointerException();
            throw new LoginException("ID错误");

    }


    public Account requestLoan(long id, double money) throws LoanException, LoginException, IOException {
        if (getAct(id) instanceof LoanSavingAccount) {
            ((LoanSavingAccount) getAct(id)).requestLoan(money);
            setSumOfLoan(money + getSumOfLoan());
            System.out.println(((LoanSavingAccount) getAct(id)) + " 成功贷款：" + money);
            Serialize();
            return ((LoanSavingAccount) getAct(id));
        } else if (getAct(id) instanceof LoanCreditAccount) {
            ((LoanCreditAccount) getAct(id)).requestLoan(money);
            setSumOfLoan(money + getSumOfLoan());
            System.out.println(((LoanCreditAccount) getAct(id)) + " 成功贷款：" + money);
            Serialize();
            return ((LoanCreditAccount) getAct(id));
        }
        return null;
    }

    public static double getSumOfLoan() { //统计所有账户贷款总数
        return SumOfLoan;
    }

    public Account payLoan(long id, double money) throws Exception {
        if (getAct(id) instanceof LoanSavingAccount) {
            LoanSavingAccount LSA = (LoanSavingAccount) getAct(id);
            if (LSA.payLoan(money)) {
                setSumOfLoan(getSumOfLoan() - money);
                System.out.println(LSA + " 成功还款：" + money);
                System.out.println(LSA + " 剩余待还：" + LSA.getLoan());
                Serialize();
                return LSA;
            }
        } else if (getAct(id) instanceof LoanCreditAccount) {
            LoanCreditAccount LCA = (LoanCreditAccount) getAct(id);
            if (LCA.payLoan(money)) {
                setSumOfLoan(getSumOfLoan() - money);
                System.out.println(LCA + " 成功还款：" + money);
                System.out.println(LCA + " 剩余待还：" + LCA.getLoan());
                Serialize();
                return LCA;
            }
        }
        throw new Exception("账户权限错误");
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
    public boolean Serialize() throws IOException {
        File AccSVD=new File("D:\\C\\Java\\ATM_8\\accounts.txt");
        if (!AccSVD.exists()) {
            try {
                AccSVD.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream=new FileOutputStream(AccSVD);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(Accounts);
        objectOutputStream.flush();
        objectOutputStream.close();
        outputStream.close();
        return true;
    }

    public boolean Deserialize() throws IOException, ClassNotFoundException {
        File AccSVD=new File("D:\\C\\Java\\ATM_8\\accounts.txt");
        if (!AccSVD.exists()) {
            return false;
        }
        FileInputStream fileInputStream=new FileInputStream(AccSVD);
        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        List<Account> temp = (ArrayList<Account>)objectInputStream.readObject();
        Accounts.addAll(temp);
        objectInputStream.close();
        fileInputStream.close();
        return true;
    }
}